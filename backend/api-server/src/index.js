import Koa from 'koa';
import Router from '@koa/router';
import logger from 'koa-logger';
import koaBody from 'koa-body';
import http from 'http';
import cors from '@koa/cors';
import dotenv from 'dotenv';
dotenv.config();

import api  from './api';

const app = new Koa();
const router = new Router();

app.use(cors());
app.use(logger());
app.use(koaBody());

app.use(router.routes()).use(router.allowedMethods());

router.use('/api', api.routes());

let serverCallback = app.callback();
let httpServer = http.createServer(serverCallback);

httpServer.listen(4000, ()=>{console.log("success 4000")})