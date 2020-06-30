<template>
  <div class="home" :style="backgroundDiv">
    <div class="slogan">房链，公开透明的不动产交易</div>
    <div class="search-box">
      <el-input placeholder="输入房屋编号" v-model="estateName"></el-input>
      <!--el-input placeholder="输入房主" v-model="homeowner"></el-input-->
      <el-button plain type="success" icon="el-icon-search" circle @click="clickSearch"></el-button>
    </div>

    <div class="show-box" v-show="isShow">
      <div class="title">房屋信息</div>
      <div class="estate">
        <div class="line">
          <div class="item">
            <span class="label">房屋编号：</span>
            <span class="value">{{estate.id}}</span>
          </div>
        </div>
        <div class="line">
          <div class="item">
            <span class="label">房主：</span>
            <span class="value">{{estate.owner}}</span>
          </div>
        </div>
        <div class="line">
          <div class="item">
            <span class="label">房屋位置：</span>
            <span class="value">{{estate.location}}</span>
          </div>
        </div>
        <div class="line">
          <div class="item">
            <span class="label">面积（平方米）：</span>
            <span class="value">{{estate.area}}</span>
          </div>
          <div class="item">
            <span class="label">购买用途：</span>
            <span class="value">{{estate.purpose}}</span>
          </div>
        </div>
        <div class="line">
          <div class="item">
              <span class="label">落成时间：</span>
              <span class="value">{{estate.begin_time}}</span>
          </div>
          <div class="item">
            <span class="label">产权到期时间：</span>
            <span class="value">{{estate.end_time}}</span>
          </div>
        </div>
      </div>
      <!--div class="apply-btn" @click="dialogVisible=true">申请交易</div-->
    </div>

    <!--el-dialog
      title="交 易"
      :visible.sync="dialogVisible"
      width="610px"
      center
      append-to-body
      top="20px"
    >
      <div class="dialog-cnt">
        <div class="box">
          <div class="title">交易房屋信息</div>
          <div class="line">
            <div class="const-item">
              <span class="label">房屋编号：</span>
              <span class="value">{{estate.name}}</span>
            </div>
            <div class="const-item">
              <span class="label">房主：</span>
              <span class="value">{{estate.author}}</span>
            </div>
            <div class="const-item">
              <span class="label">房产所有人：</span>
              <span class="value">{{estate.owner}}</span>
            </div>
          </div>
        </div>

        <div class="box">
          <div class="title">填写交易信息</div>
          <div class="item">
            <span class="label">购买用途：</span>
            <el-input class="input-value" v-model="estate.purpose"></el-input>
          </div>
          <div class="item">
            <span class="label">房屋地址：</span>
            <el-input class="input-value" v-model="estate.address"></el-input>
          </div>
          <div class="item">
            <span class="label">产权有效期：</span>
            <el-input class="input-value" v-model="estate.period"></el-input>
            <span>&ensp;月</span>
          </div>
          <div class="item">
            <span class="label">其他说明：</span>
            <el-input class="input-value" v-model="estate.desc"></el-input>
          </div>
        </div>

        <div class="box">
          <div class="title">填写房产所有人信息</div>
          <div class="line">
            <div class="item">
              <span class="label">姓&emsp;名：</span>
              <el-input class="input-value" v-model="estate.asker"></el-input>
            </div>
            <div class="item">
              <span class="label">联系电话：</span>
              <el-input class="input-value" v-model="estate.phone"></el-input>
            </div>
          </div>
        </div>

        <div class="box">
          <div class="title">付款方式（在线买房？）</div>
          <div class="price-line">
            <span>价&emsp;格：</span>
            <el-input class="input-value" v-model="estate.price"></el-input>
          </div>
          <div class="payway-box">
            <div class="payway">支付宝</div>
            <div class="payway">微信支付</div>
            <div class="payway">银联支付</div>
          </div>
        </div>
        <div class="dialog-footer">
          <el-button type="primary" @click="clickApply">确定申请交易</el-button>
        </div>
      </div>
    </el-dialog-->
  </div>
</template>

<script>
export default {
  name: 'home',
  components: {},
  data() {
    return {
      dialogVisible: false,
      isShow: false,
      backgroundDiv: {
        backgroundImage: 'url(' + require('../assets/img/background.jpg') + ')'
      },
      estateName: '',
      estateAuthor: 'XXX',
      estate: {
        id: 'XXX',
        location: 'XXX',
        area: 'XXX',
        owner: 'XXX',
        begin_time: 'XXX',
        purpose: 'XXX',
        end_time: 'XXX'
        // period: 'XXX',
        // desc: 'XXX',
        // asker: 'XXX',
        // phone: 'XXX',
        // price: '998'
      }
    }
  },
  methods: {
    clickSearch() {
      // To do
      if (this.$store.state.isLogin) {
        this.axios
          .get('/api/estate', {
            params: {
              privateKey: this.$store.state.privateKey,
              estateId: this.estateName
            }
          })
          .then(e => {
            let res = e.data
            let estate = res.data
            console.log(estate)
            if (res.success === 1) {
              this.isShow = true
              this.estate.id = estate.id
              this.estate.location = estate.location
              this.estate.area = estate.area
              this.estate.owner = estate.owner
              this.estate.begin_time = estate.beginTime
              this.estate.end_time = estate.endTime
              this.estate.purpose = estate.usage
              console.log(estate)
            } else {
              this.$message.info('无结果')
            }
          })
      } else {
        this.$message.error('请先登录')
      }
    }
    /* clickApply() {
      // To do
      let arr = [
        this.estate.asker,
        this.estate.phone,
        this.estate.purpose,
        this.estate.address,
        this.estate.period,
        this.estate.desc,
        this.estate.price
      ]
      let req = {
        estate:
          this.estate.name + '#' + this.estate.author + '#' + this.estate.alltime,
        to: this.estate.owner,
        info: arr.join('#'),
        privateKey: this.$store.state.privateKey
      }
      this.axios.post('/a pi/apply', req).then(e => {
        let res = e.data
        if (res.success === 1) this.$message.success(res.message)
        else this.$message.error(res.message)
      })
      /* this.$message.success('Apply success')
      this.dialogVisible = false */
    // }
  }
}
</script>

<style lang="stylus" scoped>
@import '../assets/css/default';

.home {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background-size: 100% 100%;
}

.slogan {
  margin-top: 120px;
  font-size: 2.35rem;
  color: #f2f6fC;
  text-shadow:2px 2px 10px #222;
  text-align: center;
}

.search-box {
  margin: 50px auto;
  width: 680px;
  display: flex;
  justify-content: space-between;

  .el-input {
    width: 600px;
  }
}

.show-box {
  width: 60%;
  min-width: 800px;
  margin: 80px auto;
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 10px;
  padding: 20px 30px 30px 40px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

  .title {
    font-size: 1.25rem;
    width: 30%;
    color: #606266;
    border-bottom: 1px solid #606266;
    margin-bottom: 10px;
  }

  .estate {
    .line {
      display: flex;
      justify-content: space-between;
    }

    .item {
      margin-top: 10px;
      width: 375px;
      font-size: 1.25rem;
      color: #303133;
    }

    .item2 {
      margin-top: 20px;
      font-size: 1.25rem;
      color: #303133;
    }
  }

  .apply-btn {
    width: 150px;
    line-height: 36px;
    text-align: center;
    margin: 25px (225 - 150)px 0 auto;
    background-color: #F53C3C;
    color: #dedede;
    border-radius: 10px;
    cursor: pointer;
    noselect();

    &:hover {
      background-color: #dedede;
      color: #F53C3C;
    }
  }
}

.dialog-cnt {
  // margin-left: 15px;
  .box {
    .title {
      width: 120px;
      border-bottom: 1px solid #d0d0d0;
      color: #d0d0d0;
      margin-bottom: 8px;
    }

    .line {
      display: flex;
      justify-content: space-between;
    }

    .const-item {
      width: 180px;
      margin-right: 10px;
      margin-bottom: 10px;
    }

    .item {
      // width: 180px;
      margin-right: 10px;
      margin-bottom: 10px;
    }

    .input-value {
      width: 180px;
    }

    .payway-box {
      display: flex;
      justify-content: center;

      .payway {
        cursor: pointer;
        width: 120px;
        line-height: 40px;
        text-align: center;
        border: 1px solid #d0d0d0;
        margin: 20px 10px 0;
        border-radius: 5px;
      }
    }
  }

  .dialog-footer {
    margin-top: 20px;
    text-align: center;
  }
}
</style>
