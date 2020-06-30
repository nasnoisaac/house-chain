<template>
  <div class="person-cnt">
    <div class="cnt">
      <keep-alive>
        <router-view></router-view>
      </keep-alive>
    </div>
    <center-side></center-side>
  </div>
</template>

<script>
import Side from '../../components/CenterSide.vue'

export default {
  components: {
    'center-side': Side
  },
  beforeMount() {
    if (!this.$store.state.isLogin) this.$router.push('/login')
  }
}
</script>

<style lang="stylus" scoped>
@import '../../assets/css/default';

.person-cnt {
  display: flex;
}

.cnt {
  // background-color: rgba(144, 176, 220, 0.2);
  top: _headbar_height;
  margin-right: _sidebar_width;
  width: 100%;
}
</style>
