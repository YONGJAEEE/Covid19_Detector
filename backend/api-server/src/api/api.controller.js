import mariadb from 'mariadb';//mariadb 사용 모듈
import dotenv from 'dotenv';//환경변수를 코드에서 제거하기 위한 모듈
dotenv.config();

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

  console.log(rows[0]);

  body = {
    "patient" : rows[0]['gotCha'].split(':')[0],
    "release" : rows[0]['runAway'].split(':')[0],
    "care" : rows[0]['cure'].split(':')[0],
    "dead" : rows[0]['dead'].split(':')[0],
    "add patient" : rows[0]['gotCha'].split(':')[1],
    "add release" : rows[0]['runAway'].split(':')[1],
    "add care" : rows[0]['cure'].split(':')[1],
    "add dead" : rows[0]['dead'].split(':')[1],
  };

  ctx.body = body;
  ctx.status = 200;
});

exports.location = (async (ctx,next) => {
  const { locateX } = ctx.request.body;
  const { locateY } = ctx.request.body;
  let sql,rows,status,body,option = '';

  if(category != 0){ option = `WHERE category = ${category}` }

  const thread = async() => {
    sql = `SELECT num,title,date,\`like\`,dislike FROM thread ${option} ORDER BY \`${sort}\` DESC LIMIT ${start}, 20;`;
    rows = await connection.query(sql,() =>{connection.release();});

    if(rows[0]){
      status = 200;
      body = {"contents" : rows};
    }else{
      status = 404;
      body = {"message" : "api 요청이 잘못됬거나 더 이상 페이지가 없어요!!"};
    }
  };

  await thread();
  ctx.status = status;
  ctx.body = body;
});


