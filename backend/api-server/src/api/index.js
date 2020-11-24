import Router from '@koa/router';

const api = new Router();


import apiCtrl from './api.controller';

api.get('/status', apiCtrl.status);
api.post('/location', apiCtrl.location);


module.exports = api;