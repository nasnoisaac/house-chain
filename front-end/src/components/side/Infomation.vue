<template>
  <div class="info-cnt">
    <div v-if="calType('company')">
      <div class="info-line">
        <div class="label">名称</div>
        <div class="value">{{name}}</div>
      </div>
      <div class="info-line">
        <div class="label">企业编码</div>
        <div class="value">{{code}}</div>
      </div>
      <div class="info-line">
        <div class="label">所在地</div>
        <div class="value">{{address}}</div>
      </div>
      <div class="info-line">
        <div class="label">联系电话</div>
        <div class="value">{{phone}}</div>
      </div>
      <div class="info-line">
        <div class="label">电子邮箱</div>
        <div class="value">{{email}}</div>
      </div>
    </div>
    <div v-else-if="calType('homeowner')">
      <div class="info-line">
        <div class="label">姓名</div>
        <div class="value">{{name}}</div>
      </div>
      <!--div class="info-line">
        <div class="label">身份证号码</div>
        <div class="value">{{code}}</div>
      </div>
      <div class="info-line">
        <div class="label">所在地</div>
        <div class="value">{{address}}</div>
      </div-->
      <div class="info-line">
        <div class="label">联系电话</div>
        <div class="value">{{phone}}</div>
      </div>
      <div class="info-line">
        <div class="label">私钥</div>
        <div class="value">{{key}}</div>
      </div>
    </div>
    <div v-else>
      <div class="info-line">
        <div class="label">姓名</div>
        <div class="value">{{name}}</div>
      </div>
      <div class="info-line">
        <div class="label">联系电话</div>
        <div class="value">{{phone}}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: '-',
      code: '-',
      address: '-',
      phone: '-',
      email: '-@-',
      key: '-'
    }
  },
  methods: {
    calType(type) {
      if (this.$store.state.userType === type) return true
      return false
    }
  },
  beforeMount() {
    this.axios.get('/api/userinfo', {
      params: {
        privateKey: this.$store.state.privateKey
      }
    }).then(e => {
      let res = e.data
      if (res.success === 1) {
        this.$store.commit('setUser', res.data)
      }
    })
    let user = this.$store.state.user
    if (this.$store.state.userType === 'homeowner') {
      this.name = user.name
      // this.code = user.id
      // this.address = user.location
      this.phone = user.phone
      this.key = this.$store.state.privateKey
    } else if (this.$store.state.userType === 'company') {
      this.name = user.name
      this.code = user.id
      this.address = user.location
      this.phone = user.phone
      this.email = user.email
    }
  }
}
</script>

<style lang="stylus" scoped>
_border_color = #ddd;
_border_color_light = #e9e9e9;

.info-cnt {
  border: 1px solid _border_color;
  box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.1);
  width: 600px;
  padding: 30px 15px;
  margin: 100px auto;
  border-radius: 8px;
  background-color: #fdfdfd

  .info-line {
    display: flex;
    justify-content: space-between;
    font-size: 1.125rem;
    border-bottom: 1px solid _border_color_light;
    line-height: 60px;
    padding: 0 10px;

    .label {
      color: #606166;
      font-size: 1rem;
    }

    .value {
      color: #303133;
      width: 500px;
      text-align: right;
      word-wrap: break-word;
    }

    &:first-child {
      border-top: 1px solid _border_color_light;
    }
  }
}
</style>
