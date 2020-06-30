<template>
  <div class="publish-cnt">
    <!--div class="box">
      <div class="title">上传不动产证明</div>
      <div class="upload-box">
        <input type="file" style="display: none" ref="file" @change="getFile" />
        <el-button @click="selectFile" plain>选择文件</el-button>
        <el-button
          plain
          @click="dialogVisible=true"
          size="mini"
          icon="el-icon-view"
          circle
          v-show="showHash"
        ></el-button>
        <span>&emsp;{{fileName}}</span>
      </div>
    </div-->
    <div class="box estate-box">
      <div class="title">登记信息</div>
      <div class="value-line">
        <div class="value-item">
          <div class="label">所在地：</div>
          <el-input class="myinput" v-model="form.location"></el-input>
        </div>
        <div class="value-item">
          <div class="label">面积(平方米):</div>
          <el-input class="myinput" v-model="form.area"></el-input>
        </div>
      </div>
      <div class="value-line">
        <div class="value-item">
          <div class="label">购买用途：</div>
          <el-input class="myinput" v-model="form.purpose"></el-input>
        </div>
      </div>
      <div class="value-line">
        <!--div class="value-item">
          <div class="label">房屋编号：</div>
          <el-input class="myinput" v-model="form.estate_name"></el-input>
        </div-->
        <div class="value-item">
          <div class="label">落成时间：</div>
          <el-date-picker
            style="width: 300px"
            v-model="form.begin_time"
            type="date"
            placeholder="选择日期"
          ></el-date-picker>
        </div>
        <div class="value-item">
          <div class="label">产权到期时间：</div>
          <el-date-picker
            style="width: 300px"
            v-model="form.end_time"
            type="date"
            placeholder="选择日期"
          ></el-date-picker>
        </div>
      </div>
    </div>
    <!--div class="box author-box">
      <div class="title">房主信息</div>
      <div class="value-item">
        <div class="label">房主：</div>
        <el-input class="myinput" v-model="form.author"></el-input>
      </div>
    </div-->
    <!--div class="box owner-box">
      <div class="title">不动产所有人信息</div>
      <div class="value-line">
        <div class="value-item">
          <div class="label">姓名：</div>
          <el-input class="myinput" v-model="form.owner_name"></el-input>
        </div>
        <div class="value-item">
          <div class="label">{{isCompany ? '企业编号': '身份证号码'}}：</div>
          <el-input class="myinput" v-model="form.owner_id"></el-input>
        </div>
      </div>
      <div class="value-item">
        <div class="label">所在地：</div>
        <el-input class="myinput" v-model="form.owner_addr"></el-input>
      </div>
      <div class="value-line">
        <div class="value-item">
          <div class="label">联系电话：</div>
          <el-input class="myinput" v-model="form.owner_phone"></el-input>
        </div>
        <div class="value-item">
          <div class="label">电子邮箱：</div>
          <el-input class="myinput" v-model="form.owner_email"></el-input>
        </div>
      </div>
    </div-->
    <div class="btn-box">
      <el-button type="primary" @click="submitEstate" plain>确认登记</el-button>
    </div>
    <!--el-dialog title="文件的SHA512值" :visible.sync="dialogVisible" width="30%">
      <span>{{fileHash}}</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog-->
  </div>
</template>

<script>
var SHA512 = require('crypto-js/sha512')
var Base64 = require('crypto-js/enc-base64.js')

export default {
  data() {
    return {
      // fileName: '请选择文件',
      // fileHash: '',
      // showHash: false,
      dialogVisible: false,
      form: {
        // estate_name: '',
        // publish_time: '',
        begin_time: '',
        end_time: '',
        location: '',
        area: '',
        purpose: ''
        // owner_addr: '',
        // owner_phone: '',
        // owner_email: ''
      }
    }
  },
  computed: {
    isCompany() {
      return this.$store.state.userType === 'company'
    }
  },
  methods: {
    selectFile() {
      this.$refs.file.click()
    },
    getFile(e) {
      let fileObj = e.target.files[0]
      this.fileName = fileObj.name
      var reader = new FileReader()
      reader.readAsDataURL(fileObj)
      this.showHash = true
      reader.onload = () => {
        var str = reader.result.split(',')[1]
        var urlDecoded = decodeURIComponent(str)
        var base64Decoded = Base64.parse(urlDecoded)
        var hashed = SHA512(base64Decoded).toString()
        this.fileHash = hashed
      }
    },
    submitEstate() {
      // to do
      var date = new Date()
      var datestr =
        date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
      // this.form.publish_time = datestr
      console.log('submit', datestr)

      if (
        this.$store.state.privateKey &&
        this.$store.state.isLogin
        // this.$store.state.userType === 'homeowner'
      ) {
        var req = {
          privateKey: this.$store.state.privateKey,
          location: this.form.location,
          area: this.form.area,
          beginTime: this.form.begin_time,
          endTime: this.form.end_time,
          usage: this.form.purpose
          // bin: this.fileHash,
          // mname: this.form.estate_name,
          // alltime:
          // this.form.record_time +
          //  '#' +
          //  this.form.publish_time +
          //  '#' +
          //  this.form.publish_time
        }
        const qs = require('qs')
        this.axios.post('/api/estate', qs.stringify(req)).then(res => {
          if (res.data.success === 1) {
            this.$message.success('登记成功')
          } else {
            this.$message.error('登记失败 ' + res.data.message)
          }
        })
        /* this.$message.error('登记成功 ')
      } else {
        this.$message.error('请先认证') */
      }
    }
  }
}
</script>

<style lang="stylus" scoped>
.publish-cnt {
  width: 920px;
  border: 1px solid #e9e9e9;
  box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  margin: 30px auto;
  padding: 10px 36px;

  .box {
    margin: 20px 0;

    .title {
      width: 200px;
      color: #7a7a7a;
      border-bottom: 1px solid #bababa;
    }

    .value-line {
      display: flex;
      justify-content: space-between;
    }

    .myinput {
      font-size: 1.125rem;
      width: 300px;
    }

    .value-item {
      display: flex;
      align-items: center;
      margin-top: 10px;
      margin-bottom: 15px;

      .label {
        width: 100px;
      }
    }
  }

  .upload-box {
    margin-top: 10px;

    span {
      color: #606266;
    }
  }

  .btn-box {
    text-align: center;
    margin-bottom: 20px;
  }
}
</style>
