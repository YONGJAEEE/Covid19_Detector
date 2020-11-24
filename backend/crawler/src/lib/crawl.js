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

  return loca;
});

exports.crawlGetEvery = (async () => {
  const browser = await puppeteer.launch();
  const page = await browser.newPage();
  await page.goto('http://ncov.mohw.go.kr/');

  let data = [],date = [],sql;

  data.push( await page.$eval(`body > div.wrap.nj > div.mainlive_container > div.container > div > div.liveboard_layout > div.liveNumOuter > div.liveNum > ul > li:nth-child(1) > span.num`, element => { return element.textContent; }) )
  date.push( await page.$eval(`body > div.wrap.nj > div.mainlive_container > div.container > div > div.liveboard_layout > div.liveNumOuter > div.liveNum > ul > li:nth-child(1) > span.before`, element => { return element.textContent; }) )
  

  for (let i = 2; i <= 4; i++) {
    data.push( await page.$eval(`body > div.wrap.nj > div.mainlive_container > div.container > div > div.liveboard_layout > div.liveNumOuter > div.liveNum > ul > li:nth-child(${i}) > span.num`, element => { return element.textContent; }) )
    date.push( await page.$eval(`body > div.wrap.nj > div.mainlive_container > div.container > div > div.liveboard_layout > div.liveNumOuter > div.liveNum > ul > li:nth-child(${i}) > span.before`, element => { return element.textContent; }) )
    data[i] = data[i].split('+ ')[0].split(')')[0];
    date[i] = date[i].split('+ ')[1].split(')')[0];

  }
  await browser.close();

  data[0] = data[0].split(')')[1];
  date[0] = date[0].split('+ ')[1].split(')')[0];

  console.log(data, date);

  sql = `INSERT INTO sickPeople(gotCha,runAway,cure,dead) VALUES('${data[0]}:${date[0]}','${data[1]}:${date[1]}','${data[2]}:${date[2]}','${data[3]}:${date[3]}');`;
  await connection.query(sql,() =>{connection.release();});

  return [data, date];
});