import axios from 'axios';
import puppeteer from 'puppeteer';
import mariadb from 'mariadb';
import dotenv from 'dotenv';
dotenv.config();

const connection = mariadb.createPool({
  host: process.env.host,
  user: process.env.user,
  password: process.env.password,
  database: process.env.database
});


exports.crawlGetDatas = (async (link,trNum,trData,trDate) => {
  const browser = await puppeteer.launch();
  const page = await browser.newPage();
  await page.goto(link);

  const number = await page.$$eval(trNum, (data) => data.length);
  let data = [],date = [];

  for (let i = 1; i <= number; i++) {
    data.push( await page.$eval(`${trNum}:nth-child(${i}${trData}`, element => { return element.textContent; }) )
    date.push( await page.$eval(`${trNum}:nth-child(${i}${trDate}`, element => { return element.textContent; }) )
  }
  await browser.close();

  return [data, date];
});

exports.crawledDatasNormalize = (async (data,date,outData,outDate) => {
  let list = [];

  await outData.map(async element => {
    await data.map(async (dataElement, index) => {
      if (await dataElement.includes(element)) {
        console.log(index);
        data = await data.slice(index);
        date = await date.slice(index);

        

      }
    console.log(data);
    console.log(date);
    
    });
  });


});

exports.getXY = (async (location) => {
  let loca,sql;
  const browser = await puppeteer.launch();
  const page = await browser.newPage();
  await page.goto(`https://www.google.co.kr/maps/place/${location.replace(' ','+')}`);
  await page.waitForNavigation();

  loca = await page.url().split('/')[6].split('@')[1].split(',');
  console.log(loca);

  await browser.close();

  sql = `UPDATE dataSheet SET x = '${loca[0]}', y = '${loca[1]}' WHERE location = '${location}';`;
  await connection.query(sql,() =>{connection.release();});
});