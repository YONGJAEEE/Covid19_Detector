import axios from 'axios';
import puppeteer from 'puppeteer';
import dotenv from 'dotenv';
dotenv.config();


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

  console.log(data);
  console.log(date);

  await browser.close();
});

