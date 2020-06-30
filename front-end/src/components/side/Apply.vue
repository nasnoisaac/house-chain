<template>
  <div>
    <div class="notice-box" v-for="(item, idx) in notices" :key="idx">
      <div class="header">
        <div class="line">
          <div class="item">
            <span class="label">房屋编号：</span>
            <span class="value">{{item.mname}}</span>
          </div>
          <div class="item">
            <span class="label">落成时间：</span>
            <span class="value">{{item.recordTime}}</span>
          </div>
          <div class="item">
            <span class="label">申请时间：</span>
            <span class="value">{{item.applyTime}}</span>
          </div>
          <div class="item">
            <span class="label">房&emsp;主：</span>
            <span class="value">{{item.author}}</span>
          </div>
        </div>
      </div>
      <div class="content">
        <div class="line">
          <div class="item">
            <span class="label">申请人姓名：</span>
            <span class="value">{{item.owner}}</span>
          </div>
          <div class="item">
            <span class="label">联系电话：</span>
            <span class="value">{{item.phone}}</span>
          </div>
        </div>
        <div class="line">
          <div class="item">
            <span class="label">购买用途：</span>
            <span class="value">{{item.use}}</span>
          </div>
          <div class="item">
            <span class="label">不动产地址：</span>
            <span class="value">{{item.location}}</span>
          </div>
          <div class="item">
            <span class="label">产权有效期：</span>
            <span class="value">{{item.period}}</span>
          </div>
        </div>
        <div class="item">
          <span class="label">其他说明：</span>
          <span class="value">{{item.text}}</span>
        </div>
        <!-- float btn begin -->
        <div class="btn-box">
          <el-button type="danger">拒绝</el-button>&ensp;
          <el-button type="success">同意</el-button>
        </div>
        <!-- float btn end -->
        <div class="line">
          <div class="item">
            <span class="label">是否授权：</span>
            <span class="value">{{item.valid}}</span>
          </div>
          <div class="item">
            <span class="label">价格：</span>
            <span class="value">{{item.price}}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      notices: [
        {
          mname: 'asd',
          recordTime: 'asd',
          applyTime: 'asd',
          author: 'asd',
          owner: 'asd',
          phone: 'asd',
          use: 'asd',
          location: 'asd',
          period: 'asd',
          text: 'ads',
          valid: 'asd',
          price: 'asd'
        }
      ]
    }
  },
  methods: {},
  beforeMount() {
    this.axios
      .post('/ap i/receiver', { privateKey: this.$store.state.privateKey })
      .then(e => {
        let res = e.data
        console.log(res)
        if (res.success === 0) {
          this.$message.error(res.message)
          return
        }
        let arr = res.data
        this.notices = []
        for (let i of arr) {
          let estate = i.estate.split('#')
          let info = i.info.split('#')
          let tmp = {
            start: i.start,
            to: i.to,
            mname: estate[0],
            author: estate[1],
            recordTime: estate[2],
            orderTime: estate[3],
            owner: info[0],
            phone: info[1],
            use: info[2],
            location: info[3],
            period: info[4],
            text: info[5],
            price: info[6],
            valid: i.valid
          }
          this.notices.push(tmp)
        }
      })
  }
}
</script>

<style lang="stylus" scoped>
.notice-box {
  width: 810px;
  margin: 30px auto;
  margin-bottom: 50px;
  border: 1px solid #d0d0d0;
  box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.1);
  border-radius: 8px;

  .header {
    border-bottom: 1px solid #d0d0d0;
    padding: 10px 30px 12px;
  }

  .content {
    padding: 0 30px 30px;
  }

  .line {
    display: flex;
    flex-wrap: wrap;
  }

  .item {
    margin-top: 12px;
    width: 250px;

    .label {
      color: #707377;
      width: 100px;
    }
  }

  .btn-box {
    float: right;
    text-align: right;
  }
}
</style>
