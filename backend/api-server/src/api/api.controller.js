import mariadb from 'mariadb';//mariadb 사용 모듈
import dotenv from 'dotenv';//환경변수를 코드에서 제거하기 위한 모듈
dotenv.config();

import crawlAll from '../lib/crawlAll';

const connection = mariadb.createPool({//db 연결용 변수, 내부 변수는 환경변수로 설정.
  host: process.env.host,
  user: process.env.user,
  password: process.env.password,
  database: process.env.database
});
 
 // 체크 완료
exports.status = (async (ctx,next) => {
  let sql, rows, body;

  sql = `SELECT * FROM sickPeople WHERE num = (SELECT MAX(num) FROM sickPeople);`;
  rows = await connection.query(sql,() =>{connection.release();});

  body = {
    "patient" : rows[0]['gotCha'].split(':')[0],
    "release" : rows[0]['runAway'].split(':')[0],
    "care" : rows[0]['cure'].split(':')[0],
    "dead" : rows[0]['dead'].split(':')[0],
    "add_patient" : rows[0]['gotCha'].split(':')[1],
    "add_release" : rows[0]['runAway'].split(':')[1],
    "add_care" : rows[0]['cure'].split(':')[1],
    "add_dead" : rows[0]['dead'].split(':')[1],
  };

  ctx.body = body;
  ctx.status = 200;
});

exports.location = (async (ctx,next) => {
  const { locateX } = ctx.request.body;
  const { locateY } = ctx.request.body;
  let sql,rows,status,body,count,set,distance,list = [];


  const location = async() => {
    sql = `SELECT location,x,y FROM dataSheet;`;
    rows = await connection.query(sql,() =>{connection.release();});

    rows.map(async (element, index) => {
      list.push(Math.pow(element['x'] - locateX, 2) + Math.pow(element['y'] - locateY, 2))
    });

    count = list.indexOf(Math.min.apply(null, list));
    set = Math.floor(Math.min.apply(null, list) * 10000000);

    if (set>5000) {
      distance = 0;
    }else if(set>2000){
      distance = 1;
    }else{
      distance = 2;
    }

    status = 200;
    body = {
      "locate" : rows[count]['location'],
      "x" : rows[count]['x'],
      "y" : rows[count]['y'],
      "distance" : distance
    };
  };

  await location();
  ctx.status = 200;
  ctx.body = body;
});


exports.test = (async (ctx,next) => {

  const test = async() => {
    crawlAll.crawlGet();
  };

  await test();
  ctx.status = 200;
  ctx.body = body;
});
