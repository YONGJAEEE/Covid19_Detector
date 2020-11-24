import schedule from 'node-schedule';
import mariadb from 'mariadb';

import dotenv from 'dotenv';
dotenv.config();

import db from './db';
import crawl from './lib/crawl';
import dataInput from './lib/dataInput';

const connection = mariadb.createPool({
  host: process.env.host,
  user: process.env.user,
  password: process.env.password,
  database: process.env.database
});


schedule.scheduleJob ('00 * * * * *', async () =>{
  let data, date, sql, rows;

  const insertD = (async () => {  
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
  });

  await insertD();
});

schedule.scheduleJob ('15 * * * * *', async () =>{
  let data, date, sql, rows;

  const deleteD = (async () => {  
    sql = `DELETE FROM dataSheet WHERE location LIKE '%해당%';`;
    await connection.query(sql,() =>{connection.release();});
    console.log('삭제 완료');
  });

  await deleteD();
});

schedule.scheduleJob ('20 * * * * *', async () =>{
  let data, date, sql, rows;
  console.log('좌표찾기 시작');
  const updateD = (async () => {  
    sql = `SELECT location FROM dataSheet;`;
    rows = await connection.query(sql,() =>{connection.release();});

    await rows.map(async (element,index) => { 
      data = await crawl.getXY(element['location']);
  
      sql = `UPDATE dataSheet SET x = '${data[0]}', y = '${data[1]}' WHERE location = '${element['location']}';`;
      await connection.query(sql,() =>{connection.release();});
    });
  });

  await updateD();
});