<template>
  <div>
    <div class="box">
      <div class="title">登记信息</div>
      <div class="line">
        <div class="item">
          <span class="label">房屋编号：</span>
          <span class="value">{{detail.id}}</span>
        </div>
      </div>
      <div class="line">
        <div class="item">
          <span class="label">位置：</span>
          <span class="value">{{detail.location}}</span>
        </div>
      </div>
      <div class="line">
        <div class="item">
          <span class="label">面积：</span>
          <span class="value">{{detail.area}}</span>
        </div>
        <div class="item">
          <span class="label">用途：</span>
          <span class="value">{{detail.purpose}}</span>
        </div>
      </div>
      <div class="line">
        <div class="item">
          <span class="label">落成时间：</span>
          <span class="value">{{detail.begin_time}}</span>
        </div>
        <div class="item">
          <span class="label">产权到期时间：</span>
          <span class="value">{{detail.end_time}}</span>
        </div>
      </div>
    </div>
    <!--div class="box">
      <div class="title">房主信息</div>
      <div class="item">
        <span class="label">房主：</span>
        <span class="value">{{detail.ownerName}}</span>
      </div>
    </div-->
    <div class="box">
      <div class="title">不动产所有人信息</div>
      <div class="line">
        <div class="item">
          <span class="label">姓名：</span>
          <span class="value">{{detail.ownerName}}</span>
        </div>
        <div class="item">
          <span class="label">{{isCompany ? '企业编号': '身份证号码'}}：</span>
          <span class="value">{{detail.id}}</span>
        </div>
      </div>
      <div class="item">
        <span class="label">所在地：</span>
        <span class="value">{{detail.location}}</span>
      </div>
      <div class="line">
        <div class="item">
          <span class="label">联系电话：</span>
          <span class="value">{{detail.phone}}</span>
        </div>
        <div class="item">
          <span class="label">电子邮箱：</span>
          <span class="value">{{detail.email}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['detail'],
  computed: {
    isCompany() {
      return this.$store.state.userType === 'company'
    }
  }
}
</script>

<style lang="stylus" scoped>
.box {
  margin-bottom: 20px;

  .title {
    width: 200px;
    color: #bababa;
    border-bottom: 1px solid #bababa;
  }

  .line {
    display: flex;
  }

  .item {
    margin-top: 10px;
    width: 200px;

    .label {
      width: 100px;
    }
  }
}
</style>
