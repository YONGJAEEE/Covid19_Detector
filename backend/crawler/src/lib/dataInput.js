import crawl from './crawl';
import mariadb from 'mariadb';
import dotenv from 'dotenv';
dotenv.config();

const connection = mariadb.createPool({
  host: process.env.host,
  user: process.env.user,
  password: process.env.password,
  database: process.env.database
});


exports.getDatas = (async () => {
  let sql,rows,data,date;

  sql = `SELECT * FROM setting;`;
  rows = await connection.query(sql,() =>{connection.release();});

  await rows.map(async (element, index) => {
    console.log(index,'번째',element['name']);
    [data,date] = await crawl.crawlGetDatas(element['link'], element['trNum'], element['trData'], element['trDate']);
    console.log(data,date);

    await data.map(async (dataElement, index) => {
      sql = `INSERT INTO dataSheet(city,location,date,x,y) VALUES('${element['name']}','${dataElement}','${date[index]}','null','null');`
      await connection.query(sql,() =>{connection.release();});
    });
  });



  console.log('데이터 load 시작');
});

exports.deleteLocate = (async () => {
  let sql;
  sql = `DELETE FROM dataSheet WHERE location LIKE '%해당%';`;
  await connection.query(sql,() =>{connection.release();});

  console.log('데이터 delete 시작');
});


exports.updateLocate = (async () => {
  let sql,rows,data;
  sql = `SELECT location FROM dataSheet;`;
  rows = await connection.query(sql,() =>{connection.release();});

  await rows.map(async (element,index) => { 
    data = await crawl.getXY(element['location']);
 
    sql = `UPDATE dataSheet SET x = '${data[0]}', y = '${data[1]}' WHERE location = '${location}';`;
    await connection.query(sql,() =>{connection.release();});
  });

  console.log('데이터 update 시작');
});

