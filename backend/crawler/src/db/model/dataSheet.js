module.exports = (Sequelize, sequelize) => {
  const dataSheet = sequelize.define('dataSheet', {
    
    num: {//고유 번호
      type: Sequelize.INTEGER,
      primaryKey: true,
      autoIncrement: true,
      allowNull: false
    },

    city: {//도시명
      type: Sequelize.TEXT,
      allowNull: false
    },

    location: {//경도,위도
      type: Sequelize.TEXT,
      allowNull: false
    },

    date: {//날짜
      type: Sequelize.TEXT,
      allowNull: false
    }

  }, 
  {freezeTableName: true});
 
  dataSheet.sync();
  return dataSheet;
};