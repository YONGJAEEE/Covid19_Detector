module.exports = (Sequelize, sequelize) => {
  const setting = sequelize.define('setting', {
    
    num: {//고유 번호
      type: Sequelize.INTEGER,
      primaryKey: true,
      autoIncrement: true,
      allowNull: false
    },

    name: {//이름(시도명)
      type: Sequelize.TEXT,
      allowNull: false
    },

    link: {//시도 페이지 링크
      type: Sequelize.TEXT,
      allowNull: false
    },

    trNum: {//표 행 개수 선택자
      type: Sequelize.TEXT,
      allowNull: false
    },

    trData: {//표 장소 선택자
      type: Sequelize.TEXT,
      allowNull: true
    },

    trDate: {//표 날짜 선택자
      type: Sequelize.TEXT,
      allowNull: true
    }

  }, 
  {freezeTableName: true});
 
  setting.sync();
  return setting;
};