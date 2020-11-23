import user from "./user";


module.exports = (Sequelize, sequelize) => {
  return {
    user: user(Sequelize, sequelize)
  };
};
