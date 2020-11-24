module.exports = (Sequelize, sequelize) => {
  const sickPeople = sequelize.define('sickPeople', {
    
    num: {//고유번호
      type: Sequelize.INTEGER,
      primaryKey: true,
      autoIncrement: true,
      allowNull: false
    },
    
    gotCha: {//확진환자
      type: Sequelize.TEXT,
      allowNull: false
    },

    runAway: {//격리해제
      type: Sequelize.TEXT,
      allowNull: false
    },

    cure: {//치료 중
      type: Sequelize.TEXT,
      allowNull: false
    },

    dead: {//사망
      type: Sequelize.TEXT,
      allowNull: false
    }
  }, 
  {freezeTableName: true});
 
  sickPeople.sync();
  return sickPeople;
};