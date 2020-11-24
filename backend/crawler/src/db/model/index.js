import dataSheet from "./dataSheet";
import setting from "./setting";


module.exports = (Sequelize, sequelize) => {
  return {
    dataSheet: dataSheet(Sequelize, sequelize),
    setting: setting(Sequelize, sequelize)
  };
};
