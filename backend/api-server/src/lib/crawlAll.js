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


exports.crawlGet = (async () => {
  const browser = await puppeteer.launch({
    args: ['--no-sandbox']
  });
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