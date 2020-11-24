import schedule from 'node-schedule';
import mariadb from 'mariadb';

import dotenv from 'dotenv';
dotenv.config();

import db from './db';
import crawl from './lib/crawl';

const connection = mariadb.createPool({
  host: process.env.host,
  user: process.env.user,
  password: process.env.password,
  database: process.env.database
});


schedule.scheduleJob ('10 * * * * *', async () =>{
  let data, date, sql, rows, outdate = '';
  let outdata = [
    '해당 일자 내 모든 접촉자 파악이 완료되어 동선 비공개',
    '해당 동선 내 모든 접촉자 파악이 완료되어 동선 비공개'
  ];
  
  
  sql = `SELECT location FROM dataSheet;`;
  rows = await connection.query(sql,() =>{connection.release();});

  await rows.map(async (element,index) => { 
    console.log(index);
    crawl.getXY(element['location']);
  });
  


/*
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

  sql = `DELETE FROM dataSheet WHERE location LIKE '%해당%';`;
  rows = await connection.query(sql,() =>{connection.release();});
 */
});