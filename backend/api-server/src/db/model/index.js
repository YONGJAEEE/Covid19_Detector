import dataSheet from "./dataSheet";
import setting from "./setting";
import sickPeople from "./sickPeople";


module.exports = (Sequelize, sequelize) => {
  return {
    dataSheet: dataSheet(Sequelize, sequelize),
    setting: setting(Sequelize, sequelize),
    sickPeople: sickPeople(Sequelize, sequelize)
  };
};
