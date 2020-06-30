<template>
  <div class="table-cnt">
    <el-table :data="tableData" style="width:100%" border>
      <el-table-column label="编号" type="index" :index="indexMethod" width="50"></el-table-column>
      <el-table-column label="房屋编号" width="50" prop="id"></el-table-column>
      <el-table-column label="所在地" width="160" prop="location"></el-table-column>
      <el-table-column label="面积（平方米）" width="80" prop="area"></el-table-column>
      <!--el-table-column label="购买用途" width="120" prop="purpose"></el-table-column-->
      <el-table-column label="落成时间" width="120" prop="begin_time"></el-table-column>
      <!--el-table-column label="产权到期时间" width="120" prop="end_time"></el-table-column-->
      <el-table-column label="操作" width="160">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleDetail(scope.$index, scope.row)">详情</el-button>
          <el-button size="mini" @click="handleTransfer(scope.$index, scope.row)">转让</el-button>
          <!-- el-button size="mini" @click="handleDelete(scope.$index, scope.row)">注销</el-button-->
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="不动产详情" :visible.sync="detailVisible" width="645px" append-to-body>
      <estate-detail :detail="detail"></estate-detail>
    </el-dialog>

    <el-dialog title="不动产转让" :visible.sync="transferVisible" width="645px" append-to-body>
      <estate-detail :detail="detail"></estate-detail>
      <div class="title">转让所有人信息</div>
      <div class="item">
        <span class="label">备注：</span>
        <el-input class="value" v-model="info" placeholder="请输入备注"></el-input>
      </div>
      <div class="item">
        <span class="label"> 区块链地址：</span>
        <el-input class="value" v-model="transferAddress" placeholder="请输入对方区块链地址"></el-input>
      </div>
      <div class="t-btn">
        <el-button plain type="primary" @click="sureTransfer">确认转让</el-button>
      </div>
    </el-dialog>

    <!-- el-dialog title="不动产注销" :visible.sync="deleteVisible" width="645px" append-to-body>
      <estate-detail :detail="detail"></estate-detail>
      <div class="t-btn">
        <el-button plain type="danger" @click="sureDelete">确认注销</el-button>
      </div>
    </el-dialog-->
  </div>
</template>

<script>
import HouseDetail from './HouseDetail.vue'
export default {
  components: {
    'estate-detail': HouseDetail
  },
  data() {
    return {
      detailVisible: false,
      transferVisible: false,
      deleteVisible: false,
      tableData: [
        {
          // bin: '',
          id: '',
          location: '',
          area: '',
          begin_time: '',
          end_time: '',
          purpose: ''
        }
      ],
      detail: {
        // bin: '',
        id: 'XXX',
        location: 'XXX',
        area: 'XXX',
        begin_time: '2018-09-09',
        end_time: '2088-09-09',
        purpose: 'XXX',
        ownerName: 'AAAA',
        ownerid: 'AAAA',
        ownerlocation: 'AAAA',
        phone: 'AAA',
        email: 'AAA'
      },
      transferAddress: '',
      info: ''
    }
  },

  methods: {
    setDetail(data) {
      // this.detail.bin = data.bin
      this.detail.id = data.id
      this.detail.location = data.location
      this.detail.area = data.area
      this.detail.begin_time = data.begin_time
      this.detail.end_time = data.end_time
      this.detail.purpose = data.purpose
      this.detail.ownerName = this.$store.state.user.name
      this.detail.ownerid = this.$store.state.user.id
      this.detail.ownerlocation = this.$store.state.user.location
      this.detail.phone = this.$store.state.user.phone
      this.detail.email = this.$store.state.user.email
    },
    handleDetail(index, row) {
      console.log(index, row)
      this.setDetail(row)
      this.detailVisible = true
    },
    handleTransfer(index, row) {
      this.setDetail(row)
      this.transferVisible = true
    },
    /* handleDelete(index, row) {
      this.setDetail(row)
      this.deleteVisible = true
    }, */
    indexMethod(idx) {
      return idx + 1
    },
    sureTransfer() {
      this.axios.get('/api/transfer', {
        params: {
          to: this.transferAddress,
          info: this.info,
          estateId: this.detail.id,
          privateKey: this.$store.state.privateKey
        }
      })
        .then(e => {
          let res = e.data
          console.log(res)
          if (res.success === 0) {
            this.$message.error('转让失败')
            return
          }
          this.$message.success('转让成功')
          this.transferVisible = false
        })
    }
    /* sureDelete() {
      var date = new Date()
      var datestr =
        date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
      this.axios
        .post('/a pi/cancel', {
          bin: this.detail.bin,
          alltime:
            this.detail.recordTime +
            '#' +
            this.detail.publishTime +
            '#' +
            datestr,
          privateKey: this.$store.state.privateKey
        })
        .then(e => {
          let res = e.data
          if (res.success === 0) {
            this.$message.error(res.message)
            return
          }
          this.$message.success(res.message)
        })
      //this.$message.success('Cancel success')
    } */
  },
  beforeMount() {
    this.axios
      .get('/api/myEstates', {
        params: {
          privateKey: this.$store.state.privateKey
        }
      })
      .then(e => {
        let res = e.data
        console.log(res)
        if (res.success === 0) {
          this.$message.error(res.message)
          return
        }
        let arr = res.data
        this.tableData = []
        for (let i of arr) {
          // if (!i.valid) continue
          // let alltime = i.alltime.split('#')
          let tmp = {
            // bin: i.bin,
            id: i.id,
            location: i.location,
            area: i.area,
            begin_time: i.beginTime,
            end_time: i.endTime,
            purpose: i.usage
          }
          this.tableData.push(tmp)
        }
      })
  }
}
</script>

<style lang="stylus" scoped>
.table-cnt {
  width: 800px;
  margin: 50px auto;
  border: 1px solid _border_color;
  border-radius: 8px;
  box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.1);
}

.title {
  width: 200px;
  color: #bababa;
  border-bottom: 1px solid #bababa;
}

.item {
  margin-top: 10px;

  .label {
    width: 100px;
  }

  .value {
    width: 400px;
  }
}

.t-btn {
  text-align: center;
  margin-top: 20px;
}
</style>
