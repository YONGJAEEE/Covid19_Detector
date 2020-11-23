import schedule from 'node-schedule';

import dotenv from 'dotenv';
dotenv.config();

//import db from './db';
import crawl from './lib/crawl';


schedule.scheduleJob ('*/10 * * * * *', async () =>{

//제주
await crawl.crawlGetDatas('https://www.jeju.go.kr/corona19.jsp', '#copperList3 > tr', ') > td:nth-child(4)', ') > td:nth-child(5)');
//부산
await crawl.crawlGetDatas('http://www.busan.go.kr/covid19/Corona19/travelhist.do', '#contents > div > div.corona_list > div > table > tbody > tr', ') > td:nth-child(5)', ') > td:nth-child(6) > p');

});