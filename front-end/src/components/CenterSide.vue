<template>
  <div class="side-cnt">
    <div v-for="(item, idx) in sideList" :key="idx">
      <div :class="calOptionStyle(idx)" @click="clickOption(item,idx)">{{item.title}}</div>
    </div>
    <div class="option" @click="logout">退出登录</div>
  </div>
</template>

<script>
const companyList = [
  { title: '用户信息', path: '/center/info' },
  // { title: '交易订单', path: '/center/order' },
  { title: '所有不动产', path: '/center/houses' },
  { title: '登记不动产', path: '/center/publish' }
  // { title: '不动产转让', path: '/center/houses' },
  // { title: '交易申请', path: '/center/apply' }
  // { title: '版权共享', path: '/center/share' }
]

const homeownerList = [
  { title: '用户信息', path: '/center/info' },
  // { title: '交易订单', path: '/center/order' },
  { title: '所有不动产', path: '/center/houses' }
  // { title: '登记不动产', path: '/center/publish' }
  // { title: '不动产转让', path: '/center/houses' },
  // { title: '交易申请', path: '/center/apply' }
]

const userList = [
  { title: '用户信息', path: '/center/info' }
  // { title: '交易订单', path: '/center/order' }
]

export default {
  data() {
    return {
      active: 0
    }
  },
  computed: {
    sideList() {
      if (this.$store.state.userType === 'company') return companyList
      else if (this.$store.state.userType === 'homeowner') return homeownerList
      else return userList
    }
  },
  watch: {
    $route() {
      this.active = this.$route.meta.side
    }
  },
  methods: {
    calOptionStyle(id) {
      if (this.active === id) return 'option option-active'
      else return 'option'
    },
    clickOption(item, idx) {
      if (this.$route.path === item.path) return
      if (this.active === idx) return
      this.active = idx
      this.$router.push(item.path)
    },
    logout() {
      this.$store.commit('logout')
      this.$router.push('/login')
    }
  },
  beforeMount() {
    this.active = this.$route.meta.side
  }
}
</script>

<style lang="stylus" scoped>
@import '../assets/css/default';

.side-cnt {
  position: fixed;
  width: _sidebar_width;
  top: _headbar_height;
  right: 0;
  bottom: 0;
  border-right: 1px solid #d0d0d0;
  z-index: 1000;
  text-align: center;
  padding-top: 40px;
}

.option {
  font-size: 1.25rem;
  line-height: 60px;
  cursor: pointer;
  noselect();

  &:hover {
    background-color: #eee;
  }
}

.option-active {
  color: _color_default;
  border-left: 3px solid _color_default;
  border-right: 3px solid _color_default;
}
</style>
