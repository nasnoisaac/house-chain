<template>
  <div class="main-cnt">
    <div class="bg"></div>
    <div class="top-right">
      <div class="tr-btn-box" v-show="!isLogin">
        <div class="tr-btn" @click="loginVisible=true">机构登录</div>
        <!--div class="tr-btn" @click="registerVisible=true">机构注册</div-->
      </div>
      <div class="tr-name" v-show="isLogin">{{more.name}}</div>
    </div>
    <div class="search-cnt">
      <div class="search-title">查询房产信息</div>
      <div class="search-box">
        <el-input placeholder="输入房屋编号(起)" v-model="estateId"></el-input>
        <el-input placeholder="输入房屋编号(止)" v-model="estateId_end"></el-input>
        <el-button plain type="success" icon="el-icon-search" circle @click="clickSearch"></el-button>
      </div>
    </div>

    <div class="more-cnt">
      <div class="switch-box">
        <div :class="switchClass(1)" @click="switchItem(1)">房产信息</div>
        <div :class="switchClass(2)" @click="switchItem(2)">历史记录</div>
        <div :class="switchClass(3)" @click="switchItem(3)">机构信息</div>
      </div>
      <div class="cnts">
        <div v-if="isLogin">
          <div class="copyright-box" v-show="isShowBox(1)">
            <div class="table-cnt">
                <el-table :data="tableData_e" style="width:100%" border>
                <el-table-column label="编号" type="index" :index="indexMethod" width="50"></el-table-column>
                <el-table-column label="房屋编号" width="50" prop="id"></el-table-column>
                <el-table-column label="房主" width="200" prop="owner"></el-table-column>
                <el-table-column label="所在地" width="200" prop="location"></el-table-column>
                <el-table-column label="面积（平方米）" width="60" prop="area"></el-table-column>
                <el-table-column label="购买用途" width="120" prop="purpose"></el-table-column>
                <el-table-column label="落成时间" width="120" prop="begin_time"></el-table-column>
                <el-table-column label="产权到期时间" width="120" prop="end_time"></el-table-column>
                </el-table>
            </div>
            <!--div class="--box">
              <div class="title">登记信息</div>
              <div class="line">
                <div class="item">
                  <span class="label">房屋编号：</span>
                  <span class="value">{{estate.id}}</span>
                </div>
                <div class="item">
                  <span class="label">落成时间：</span>
                  <span class="value">{{estate.begin_time}}</span>
                </div>
                <div class="item">
                  <span class="label">产权到期时间：</span>
                  <span class="value">{{estate.end_time}}</span>
                </div>
                <div class="--box">
                    <div class="line">
                    <div class="item">
                      <span class="label">所在地：</span>
                      <span class="value">{{estate.location}}</span>
                    </div>
                    <div class="item">
                      <span class="label">面积（平方米）：</span>
                      <span class="value">{{estate.area}}</span>
                    </div>
                    </div>
                    <div class="item">
                    <span class="label">购买用途：</span>
                    <span class="value">{{estate.purpose}}</span>
                    </div>
                  <div class="line">
                    <div class="item">
                      <span class="label">联系电话：</span>
                      <span class="value">{{estate.phone}}</span>
                    </div>
                    <div class="item">
                      <span class="label">电子邮箱：</span>
                      <span class="value">{{estate.email}}</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="--box">
              <div class="title">房主信息</div>
              <div class="item">
                <span class="label">房主：</span>
                <span class="value">{{estate.owner}}</span>
              </div>
            </div-->
          </div>
          <div class="history-box" v-show="isShowBox(2)">
            <div class="search-box">
              <el-input placeholder="输入转让人地址" v-model="from"></el-input>
              <el-input placeholder="输入被转让人地址" v-model="to"></el-input>
              <el-button plain type="success" icon="el-icon-search" circle @click="clickSearch_h"></el-button>
            </div>
            <div class="table-cnt">
                <el-table :data="history" style="width:100%" border>
                <el-table-column label="编号" type="index" :index="indexMethod" width="50"></el-table-column>
                <el-table-column label="房屋编号" width="80" prop="estateId"></el-table-column>
                <el-table-column label="原所有人" width="200" prop="from"></el-table-column>
                <el-table-column label="交易对象" width="200" prop="to"></el-table-column>
                <el-table-column label="交易时间" width="120" prop="time"></el-table-column>
                <el-table-column label="其他信息" width="120" prop="info"></el-table-column>
                </el-table>
            </div>
            <!--div class="--box">
              <div class="title">交易记录</div>
              <div class="line">
                <div class="item">
                  <span class="label">原所有人：</span>
                  <span class="value">{{history.from}}</span>
                </div>
                <div class="item">
                  <span class="label">交易对象：</span>
                  <span class="value">{{history.to}}</span>
                </div>
                <div class="item">
                  <span class="label">房屋编号：</span>
                  <span class="value">{{history.estateId}}</span>
                </div>
                <div class="item">
                  <span class="label">交易时间：</span>
                  <span class="value">{{history.time}}</span>
                </div>
                <div class="item">
                  <span class="label">其他信息：</span>
                  <span class="value">{{history.info}}</span>
                </div>
              </div>
            </div-->
          </div>
          <div class="info-box" v-show="isShowBox(3)">
            <div class="item">
              <span>机构名称：</span>
              <span>{{more.name}}</span>
            </div>
            <div class="item">
              <span>所在地：</span>
              <span>{{more.location}}</span>
            </div>
            <div class="item">
              <span>联系方式：</span>
              <span>{{more.phone}}</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- login dialog -->
    <el-dialog title="机构登录" :visible.sync="loginVisible" width="30%" center append-to-body>
      <div class="ld-value">
        <div class="label">私钥：</div>
        <el-input class="myinput" v-model="privateKey" placeholder="请输入私钥"></el-input>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="clickLogin">登 录</el-button>
      </div>
    </el-dialog>
    <!-- register dialog -->
    <!--el-dialog title="机构注册" :visible.sync="registerVisible" width="30%" center append-to-body>
      <div class="d-cnt">
        <div class="d-line">
          <div class="label">姓名：</div>
          <el-input class="value" v-model="register.name"></el-input>
        </div>
        <div class="d-line">
          <div class="label">所在地：</div>
          <el-input class="value" v-model="register.location"></el-input>
        </div>
        <div class="d-line">
          <div class="label">联系电话：</div>
          <el-input class="value" v-model="register.phone"></el-input>
        </div>
        <div class="d-line">
          <div class="label">电子邮箱：</div>
          <el-input class="value" v-model="register.email"></el-input>
        </div>
      </div>
      <div slot="footer">
        <el-button type="primary" @click="clickRegister">确定申请</el-button>
      </div>
    </el-dialog-->
  </div>
</template>

<script>
export default {
  data() {
    return {
      loginVisible: false,
      registerVisible: false,
      active: 1,
      estateId: '',
      estateId_end: '',
      from: '',
      to: '',
      showLogin: true,
      privateKey: 'key',
      tableData_e: [
        {
          id: '',
          location: '',
          area: '',
          owner: '',
          begin_time: '',
          purpose: '',
          end_time: ''
        }
      ],
      history: [
        {
          form: '',
          to: '',
          estateId: '',
          time: '',
          info: ''
        }
      ],
      // register: {
      //   name: 'XXX',
      //   location: 'XXX',
      //   phone: 'XXX',
      //   email: 'XXX',
      //   privateKey: this.$store.state.privateKey
      // },
      more: {
        name: '-',
        location: '-',
        phone: '-'
      }
    }
  },
  computed: {
    isLogin() {
      if (this.$store.state.isLogin && this.$store.state.userType === 'judge') {
        return true
      }
      return false
    }
  },
  methods: {
    switchItem(type) {
      if (this.active === type) return
      this.active = type
    },
    switchClass(type) {
      if (this.active === type) return 'switch-item switch-item-active'
      return 'switch-item'
    },
    isShowBox(item) {
      if (this.active === item) return true
      return false
    },
    indexMethod(idx) {
      return idx + 1
    },
    clickSearch() {
      // to do
      if (!this.isLogin) {
        this.$message.error('请先登录')
        return
      }
      this.axios
        .get('/api/estates', {
          params: {
            from: this.estateId,
            to: this.estateId_end,
            privateKey: this.$store.state.privateKey
          }
        })
        .then(e => {
          let res = e.data
          if (res.success === 0) {
            this.$message.error('无结果')
            return
          }
          let arr = res.data
          // let user = res.data.user
          this.tableData_e = []
          for (let estate of arr) {
            let tmp = {
              id: estate.id,
              location: estate.location,
              owner: estate.owner,
              area: estate.area,
              begin_time: estate.beginTime,
              end_time: estate.endTime,
              purpose: estate.usage
            }
            this.tableData_e.push(tmp)
          }
        })
    },
    clickSearch_h() {
      this.axios
        .get('/api/records', {
          params: {
            from: this.from,
            to: this.to,
            privateKey: this.$store.state.privateKey
          }
        })
        .then(e => {
          let res = e.data
          if (res.success === 0) {
            this.$message.error('无结果')
            return
          }
          let arr = res.data
          this.history = []
          for (let history of arr) {
            let tmp = {
              from: history.from,
              to: history.to,
              estateId: history.estateId,
              time: history.time,
              info: history.info
            }
            this.history.push(tmp)
          }
        })
    },
    clickLogin() {
      // to do
      this.axios
        .get('/api/userinfo', {
          params: {
            privateKey: this.privateKey
          }
        })
        .then(res => {
          res = res.data
          console.log(res)
          if (res.success === 0) {
            this.$message.error('私钥错误')
          } else {
            let user = res.data
            this.$store.commit('initSetState', {
              privateKey: this.privateKey,
              userType: user.kind,
              isLogin: true
            })
            this.$store.commit('setUserType', user.kind)
            this.$store.commit('setUser', user)
            this.more.name = user.name
            this.more.location = user.location
            this.more.phone = user.phone
            this.$message.success('登录成功')
            this.loginVisible = false
          }
        })
        .catch(e => {
          this.$message.error('私钥错误')
        })
      /* let user = {
        name: 'user',
        id: 0,
        location: 'location',
        phone: 'phone',
        email: 'email',
        kind: 'judge'
      }
      this.$store.commit('initSetState', {
        privateKey: this.privateKey,
        userType: 'judge',
        isLogin: true
      })
      this.$store.commit('setUserType', user.kind)
      this.$store.commit('setUser', user)
      this.$message.success('登录成功')
      this.loginVisible = false */
    },
    clickRegister() {
      // to do
      /* this.axios.post('/a pi/judgeapply', this.register).then(e => {
        let res = e.data
        if (res.success === 1) {
          this.$message.success('认证成功')
        } else {
          this.$message.error('认证失败')
        }
      }) */
      this.$message.success('认证成功')
      this.registerVisible = false
    }
  },
  beforeMount() {
    if (this.$store.state.userType === 'judge' &&
      this.$store.state.isLogin === true) {
      this.more.name = this.$store.state.user.name
      this.more.location = this.$store.state.user.location
      this.more.phone = this.$store.state.user.phone
    }
  }
}
</script>
<style lang="stylus" scoped>
@import '../assets/css/default';

.main-cnt {
  .bg {
    position: fixed;
    top: 0px;
    bottom: 0;
    left: 0;
    right: 0;
    background-color: rgba(215, 12, 24, 0.1);
    z-index: -1;
  }

  .top-right {
    position: absolute;
    top: 80px;
    right: 36px;

    .tr-name {
      font-size: 1.25rem;
      color: #303133;
    }

    .tr-btn {
      width: 120px;
      line-height: 36px;
      text-align: center;
      background-color: #fff;
      margin-top: 10px;
      border-radius: 8px;
      cursor: pointer;
      border: 1px solid #b7b7b7;
      noselect();

      &:hover {
        background-color: #f5f5f5;
        color: _color_default;
        border: 1px solid _color_default;
      }
    }
  }

  .search-cnt {
    width: 680px;
    margin: 50px auto;

    .search-title {
      font-size: 1.5rem;
      color: _color_default;
      margin-bottom: 15px;
    }

    .search-box {
      display: flex;
      justify-content: space-between;

      .el-input {
        box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.1);
        width: 300px;
      }
    }
  }
}

.more-cnt {
  width: 1000px;
  padding: 30px 10px 20px 30px;
  margin: 50px auto;
  background-color: #fefefe;
  border-radius: 8px;
  min-height: 360px;
  box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.1);

  .switch-box {
    width: 360px;
    display: flex;
    border: 1px solid #c7c7cc;
    line-height: 45px;
    margin-bottom: 20px;
    border-radius: 8px;

    .switch-item {
      cursor: pointer;
      noselect();
      width: 120px;
      text-align: center;
      border-right: 1px solid #c7c7cc;

      &:first-child {
        border-top-left-radius: 8px;
        border-bottom-left-radius: 8px;
      }

      &:last-child {
        border-right: 0px solid #c7c7cc;
        border-top-right-radius: 8px;
        border-bottom-right-radius: 8px;
      }

      &:hover {
        background-color: #dedede;
        color: _color_default;
      }
    }

    .switch-item-active {
      background-color: #eee;
      color: _color_default;
    }
  }
}

.copyright-box {

  .table-cnt {
    width: 900px;
    margin: 50px auto;
    border: 1px solid _border_color;
    border-radius: 8px;
    box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.1);
  }
  .--box {
    margin-bottom: 20px;

    .title {
      color: #909399;
      width: 120px;
      border-bottom: 1px solid #909399;
    }

    .line {
      display: flex;
    }

    .item {
      width: 250px;
      margin-top: 8px;
    }
  }
}

.history-box {

  .search-box {
      display: table;
      margin: 50px auto;
      justify-content: space-between;

      .el-input {
        box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.1);
        width: 300px;
      }
    }

  .table-cnt {
    width: 800px;
    margin: 50px auto;
    border: 1px solid _border_color;
    border-radius: 8px;
    box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.1);
  }
}

.info-box {
  margin-top: 80px;
  margin-left: 30px;

  .item {
    margin-top: 30px;
  }
}

.ld-value {
  display: flex;
  justify-content: space-between;
  align-items: center;

  .label {
    font-size: 1.125rem;
  }

  .myinput {
    width: 350px;
  }
}

.d-cnt {
  width: 450px;
  margin: 0 auto;

  .d-line {
    display: flex;
    align-items: center;
    margin-bottom: 20px;

    &:last-child {
      margin-bottom: 0;
    }

    .label {
      width: 90px;
    }

    .value {
      width: 300px;
    }
  }
}
</style>
