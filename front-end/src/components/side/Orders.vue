<template>
  <div class="order-cnt">
    <div class="order-box" v-for="(order,idx) in orderList" :key="idx">
      <div class="head">
        <div class="item item1">
          <span class="label">房屋编号：</span>
          <span class="value">{{order.name}}</span>
        </div>
        <div class="item item2">
          <span class="label">落成时间：</span>
          <span class="value">{{order.record_time}}</span>
        </div>
        <div class="item item3">
          <span class="label">订单时间：</span>
          <span class="value">{{order.order_time}}</span>
        </div>
      </div>
      <div class="main">
        <div class="line">
          <div class="item item1">
            <span class="label">房主：</span>
            <span class="value">{{order.author}}</span>
          </div>
        </div>
        <div class="line">
          <div class="item item1">
            <span class="label">是否交易：</span>
            <span class="value">{{order.auth_str}}</span>
          </div>
          <div class="item item2">
            <span class="label">价格：</span>
            <span class="value">{{order.price}}</span>
          </div>
          <div class="item item3">
            <div :class="order.is_auth ? 'certify' : 'certify-disable' ">电子授权证明</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
var testList = [
  {
    name: '这是一个名称',
    author: '这是房主',
    is_auth: true,
    auth_str: '是',
    price: 1000000,
    order_time: '2019-09-09',
    record_time: '2018-09-09'
  }
]

export default {
  data() {
    return {
      orderList: testList
    }
  },
  beforeMount() {
    this.axios
      .post('/a pi/orders', { privateKey: this.$store.state.privateKey })
      .then(e => {
        let res = e.data
        if (res.success === 0) {
          this.$message.error(res.message)
          return
        }
        let arr = res.data
        this.orderList = []
        for (let i of arr) {
          let estate = i.estate.split('#')
          let info = i.info.split('#')
          let tmp = {
            start: i.start,
            to: i.to,
            name: estate[0],
            author: estate[1],
            record_time: estate[2],
            order_time: estate[3],
            owner: info[0],
            phone: info[1],
            use: info[2],
            location: info[3],
            period: info[4],
            desc: info[5],
            price: info[6],
            is_auth: i.valid,
            auth_str: i.valid ? '是' : '否'
          }
          this.orderList.push(tmp)
        }
      })
  }
}
</script>

<style lang="stylus" scoped>
@import '../../assets/css/default';

.order-box {
  width: 800px;
  margin: 60px auto;
  border: 1px solid #e9e9e9;
  box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.1);
  border-radius: 8px;

  .head, .line {
    display: flex;
    justify-content: space-between;
  }

  .head {
    padding: 25px 20px 20px 20px;
    border-bottom: 1px solid #dedede;
  }

  .main {
    padding: 10px 20px 20px 20px;
  }

  .line {
    line-height: 40px;
    margin-top: 10px;
  }

  .item {
    .label {
      color: #a0a0a0;
    }

    .value {
      color: #303133;
    }
  }

  .item1 {
    width: 300px;
  }

  .item2 {
    width: 240px;
  }

  .item3 {
    width: 220px;
  }

  .certify {
    cursor: pointer;
    width: 150px;
    border: 1px solid _color_default;
    color: _color_default;
    text-align: center;
    border-radius: 10px;
    noselect();

    &:hover {
      background-color: _color_default;
      color: #fff;
    }
  }

  .certify-disable {
    cursor: not-allowed;
    width: 150px;
    border: 1px solid #d0d0d0;
    color: #d0d0d0;
    text-align: center;
    border-radius: 10px;
    noselect();
  }
}
</style>
