<template>
  <div class="header-cnt">
    <div class="option-box">
      <div :class="calOption(1)" @click="clickOption(1)">不动产</div>
      <div :class="calOption(2)" @click="clickOption(2)">房主</div>
      <div :class="calOption(3)" @click="clickOption(3)">企业</div>
      <div :class="calOption(4)" @click="clickOption(4)">仲裁区</div>
      <div :class="calOption(5)" @click="clickOption(5)">{{ showCenter }}</div>
    </div>
    <div class="img-box">
      <img id="scut" src="../assets/img/scut1.png" alt="SCUT" />
      <img id="webank" src="../assets/img/webank1.png" alt="webank" />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      active: 1
    }
  },
  computed: {
    showCenter() {
      if (this.$store.state.isLogin) return '个人中心'
      return '登录/注册'
    }
  },
  watch: {
    $route: function() {
      this.active = this.$route.meta.idx
    }
  },
  methods: {
    calOption(num) {
      return this.active === num ? 'option option-active' : 'option'
    },
    clickOption(item) {
      if (this.active === item) return
      this.active = item
      switch (item) {
        case 1:
          this.$router.push('/')
          break
        case 2:
          this.$router.push('/homeowner')
          break
        case 3:
          this.$router.push('/company')
          break
        case 4:
          this.$router.push('/arbitration')
          break
        case 5:
          if (this.$store.state.isLogin) this.$router.push('/center/info')
          else this.$router.push('/login')
          break
        default:
          break
      }
    }
  },
  beforeMount() {
    this.active = this.$route.meta.idx
  }
}
</script>

<style lang="stylus" scoped>
@import '../assets/css/default';

$head-height = 60px;
$option-active = _color_default;

.header-cnt {
  position: fixed;
  height: $head-height;
  border-bottom: 1px solid #ddd;
  display: flex;
  justify-content: space-between;
  background-color: rgba(250, 250, 250, 1);
  // background-color: #E2E6EC;
  top: 0;
  right: 0;
  left: 0;
  z-index: 100;
}

.img-box {
  display: flex;
  align-items: center;
  margin-right: 50px;

  #scut {
    width: 45px;
    height: 45px;
  }

  #webank {
    height: 32px;
    margin-left: 20px;
  }
}

.option-box {
  display: flex;
  line-height: $head-height;
  margin-left: 50px;

  .option {
    font-size: 1.125rem;
    color: #555;
    // font-weight: bold;
    padding: 0 25px;
    cursor: pointer;

    &:hover {
      background-color: #ddd;
    }
  }

  .option-active {
    color: $option-active;
    border-bottom: 3px solid $option-active;
  }
}
</style>
