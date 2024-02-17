<template>
  <div>
    <!-- 卡片区域 -->
    <el-card>
      <!-- 搜索框 -->
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="请输入内容" v-model="searchfilename">
            <el-button @click="searchFiles" slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </el-col>
      </el-row>

      <!-- 展示列表 -->
      <el-table :data="musicList" border stripe>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="文件">
          <template slot-scope="scope">
            <div v-if="scope.row.isDirectory === 1">
              <i class="el-icon-folder-opened" style="color: green; font-size: 200%"
                @click="sonFileList(scope.row.filePath)"></i>
              <span @click="sonFileList(scope.row.filePath)">{{
                scope.row.fileName
              }}</span>
            </div>
            <div v-else>
              <i class="el-icon-takeaway-box" style="color: red; font-size: 200%"></i>
              {{ scope.row.fileName }}
            </div>
          </template>
        </el-table-column>
        <el-table-column label="路径" prop="filePath"></el-table-column>
        <el-table-column label="大小" prop="fileSize"></el-table-column>
        <el-table-column label="时间" prop="fileTime"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!-- 还原 -->
            <el-button style="margin-left: 2px" v-model="scope.row.id" type="success" icon="el-icon-refresh-left"
              size="mini"></el-button>
            <!-- 永久删除 -->
            <el-button style="margin-left: 2px" @click="deleteFile(scope.row.filePath)" v-model="scope.row.filePath"
              type="danger" icon="el-icon-delete" size="mini"></el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum" :page-sizes="[5, 8]" :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      queryInfo: {
        query: '',
        pagenum: 1,
        pagesize: 5,
        basepath: '/' + window.sessionStorage.getItem('token')
      },
      searchfilename: '',
      musicList: [],
      filePathList: [],
      filepath: '/' + window.sessionStorage.getItem('token'),
      total: 0
    }
  },
  created() {
    this.getMusicList()
  },
  methods: {
    async getMusicList() {
      const { data: res } = await this.$http.get('rubbishListsFiles?filepath=' + this.filepath)

      if (res.code != 200) return this.$message.error('获取用户列表失败')
      // 赋值
      this.musicList = res.data.slice(
        (this.queryInfo.pagenum - 1) * this.queryInfo.pagesize,
        this.queryInfo.pagesize * this.queryInfo.pagenum)
      this.total = res.total
    },
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize
      this.getMusicList(this.filepath)
    },
    handleCurrentChange(newPage) {
      this.queryInfo.pagenum = newPage
      this.getMusicList(this.filepath)
    },
    // 删除
    deleteFile(deleteFilepath) {
      const that = this
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          that.$http
            .get('deleteRubbishFiles?deleteFilepath=' + deleteFilepath)
            .then(function (res) {
              that.getMusicList(that.filepath)
            })

          this.$message({
            type: 'success',
            message: '永久删除成功!'
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    // 修改
    alterFile(filepath) {
      console.log('idhhgedh')
    },
    // 下载
    downLoadFile(filepath) {
      this.$http.get('downLoadFile?filePath=' + filepath).then(function (res) {
        window.open(res.data)
      })
    },
    searchFiles() {
      const that = this
      this.$http
        .get('searchFiles22?searchfilename=' + this.searchfilename)
        .then(function (res) {
          console.log(res)
          that.musicList = res.data.data
          that.total = res.data.total
        })
    }
  }
}
</script>

<style lang="less" scoped></style>
