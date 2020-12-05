import mariadb from 'mariadb';

import dotenv from 'dotenv';
dotenv.config();

const connection = mariadb.createPool({
  host: process.env.host,
  user: process.env.user,
  password: process.env.password,
  database: process.env.database
});

let initializer = [
  ['제주','https://www.jeju.go.kr/corona19.jsp', '#copperList3 > tr', ') > td:nth-child(4)', ') > td:nth-child(5)'],
  ['부산','http://www.busan.go.kr/covid19/Corona19/travelhist.do', '#contents > div > div.corona_list > div > table > tbody > tr', ') > td:nth-child(5)', ') > td:nth-child(6) > p']
];

const insertData = (async () => {
  let sql;

  await initializer.map(async (element, index) => {
    sql = `INSERT INTO setting(name,link,trNum,trData,trDate) 
    VALUES('${initializer[index][0]}','${initializer[index][1]}','${initializer[index][2]}','${initializer[index][3]}','${initializer[index][4]}');`
    console.log(sql);
    await connection.query(sql,() =>{connection.release();});
  });

});

insertData();