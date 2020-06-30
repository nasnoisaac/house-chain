<template>
  <div id="app">
    <head-bar></head-bar>
    <router-view class="rv"></router-view>
  </div>
</template>

<script>
import HeadBar from './components/HeadBar.vue'
export default {
  name: 'app',
  components: {
    'head-bar': HeadBar
  },
  beforeMount() {
    var privateKey = localStorage.getItem('privateKey')
    var userType = localStorage.getItem('userType')
    var isLogin = localStorage.getItem('isLogin')
    if (privateKey && userType && isLogin) {
      this.$store.commit('initSetState', {
        privateKey: privateKey,
        isLogin: isLogin,
        userType: userType
      })
      this.axios.get('/api/userinfo', {
        params: {
          privateKey: privateKey
        }
      }).then(e => {
        let res = e.data
        if (res.success === 1) {
          this.$store.commit('setUser', res.data)
        }
      })
      /* let user = {
        name: 'user',
        id: 0,
        location: 'location',
        phone: 'phone',
        email: 'email'
      }
      this.$store.commit('setUser', user) */
    }
  }
}
</script>

<style>
* {
  margin: 0px;
  padding: 0px;
}
.rv {
  padding-top: 60px;
}

#app {
  /* font-family: 'Avenir', Helvetica, Arial, sans-serif; */
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  /* margin-top: 60px; */
}
</style>
