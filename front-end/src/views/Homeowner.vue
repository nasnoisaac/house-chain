<template>
  <div class="main-cnt" :style="backgroundDiv">
    <div class="estate-btn" @click="dialogVisible=true">申请房主认证</div>
    <el-dialog title="申请房主认证" :visible.sync="dialogVisible" center width="40%" append-to-body>
      <div class="d-cnt">
        <div class="d-line">
          <div class="label">姓名：</div>
          <el-input class="value" v-model="form.name"></el-input>
        </div>
        <!--div class="d-line">
          <div class="label">身份证号码：</div>
          <el-input class="value" v-model="form.id"></el-input>
        </div>
        <div class="d-line">
          <div class="label">所在地：</div>
          <el-input class="value" v-model="form.location"></el-input>
        </div-->
        <div class="d-line">
          <div class="label">联系电话：</div>
          <el-input class="value" v-model="form.phone"></el-input>
        </div>
        <!--div class="d-line">
          <div class="label">电子邮箱：</div>
          <el-input class="value" v-model="form.email"></el-input>
        </div-->
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="clickApply">确定申请</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dialogVisible: false,
      backgroundDiv: {
        backgroundImage: 'url(' + require('../assets/img/estate.jpg') + ')'
      },
      form: {
        // id: '',
        name: '',
        // location: '',
        // email: '',
        phone: ''
        // privateKey: this.$store.state.privateKey
      }
    }
  },
  methods: {
    clickApply() {
      const qs = require('qs')
      this.axios.post('/api/register', qs.stringify(this.form)).then(e => {
        let res = e.data
        if (res.success === 1) {
          this.$message.success('认证成功,已自动登录')
          this.dialogVisible = false
          this.$store.commit('initSetState', {
            privateKey: res.data.privateKey,
            isLogin: true,
            userType: 'homeowner'
          })
          this.$store.commit('setUser', this.form)
          console.log(res.data.address)
        } else {
          this.$message.error('认证失败,' + res.message)
        }
      })
      /* this.$message.success('认证成功')
      this.$store.commit('setUserType', 'homeowner')
      this.dialogVisible = false */
    }
  }
}
</script>

<style lang="stylus" scoped>
@import '../assets/css/default';

.main-cnt {
  position: fixed;
  top: 0px;
  bottom: 0;
  left: 0;
  right: 0;
  background-size: 100% 100%;
}

.estate-btn {
  background-color: #fff;
  width: 200px;
  line-height: 50px;
  border-radius: 10px;
  margin: 80px auto;
  text-align: center;
  font-size: 1.25rem;
  noselect();
  cursor: pointer;
  box-shadow: 0 2px 12px 0 rgba(120, 120, 120, 0.5);

  &:hover {
    background-color: #eee;
    color: _color_default;
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
      width: 360px;
    }
  }
}
</style>
