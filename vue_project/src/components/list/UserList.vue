<template>
  <div>
    <div style="overflow: hidden">
      <div v-for="item in filePathList" :key="item.id" style="float: left">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item>
            <a @click="sonFileList(item.absolutePath)">{{ item.basePath }}</a>
          </el-breadcrumb-item>
          <el-breadcrumb-item></el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <el-breadcrumb style="float: right" separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/home' }">
          <a href="#">回到首页</a>
        </el-breadcrumb-item>
        <el-breadcrumb-item></el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 卡片区域 -->
    <el-card>
      <!-- 搜索框 -->
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="请输入内容" v-model="searchfilename">
            <el-button @click="searchFiles" slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" @click="addFileVisible = true">创建目录</el-button>
        </el-col>
        <el-col :span="2" style="margin-left: 30px">
          <el-upload ref="upload" class="upload-demo" action="#"
            accept=".xlsx, .xls, .mp4, .jpg, .png, .txt, .mp3, .avi, .doc, .docx, .jpeg, .pdf ,.zip ,.md ,.pptx"
            :auto-upload="false" :on-change="uploadFile" :show-file-list="false">
            <el-button type="warning">上传文件</el-button>
          </el-upload>
        </el-col>
      </el-row>

      <!-- 展示列表 -->
      <el-table :data="fileList" border stripe>
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
              <i class="el-icon-document" style="color: red; font-size: 200%"></i>
              {{ scope.row.fileName }}
            </div>
          </template>
        </el-table-column>
        <el-table-column label="路径" prop="filePath"></el-table-column>
        <el-table-column label="大小" prop="fileSize"></el-table-column>
        <el-table-column label="时间" prop="fileTime"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!-- 修改 -->
            <el-button style="margin-left: 2px" @click="alterFile22(scope.row.fileName)" v-model="scope.row.fileName"
              type="primary" icon="el-icon-edit" size="mini"></el-button>
            <!-- 删除 -->
            <el-button style="margin-left: 2px" @click="deleteFile(scope.row.filePath)" v-model="scope.row.filePath"
              type="danger" icon="el-icon-delete" size="mini"></el-button>
            <!-- 收藏 -->
            <el-button style="margin-left: 2px" @click="collectionFile(scope.row.filePath)" v-model="scope.row.filePath"
              type="warning" icon="el-icon-star-off" size="mini"></el-button>
            <!-- 下载 -->
            <el-button v-if="scope.row.isDirectory != 1" style="margin-left: 2px"
              @click="downLoadFile(scope.row.filePath)" v-model="scope.row.filePath" type="success"
              icon="el-icon-download" size="mini"></el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区域  hide-on-single-page   当数据只有一页时,自动隐藏分页菜单-->
      <!--@size-change="handleSizeChange"
        当每页显示的数据数目发生改变生的动作,需要按新的pageSize查询数据-->
      <!--@current-change="handleCurrentChange"
          当改变当前页时产生的动作-->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum" :page-sizes="[5, 8]" :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
    </el-card>

    <!-- 提示对话框 -->
    <el-dialog title="新建目录" :visible.sync="addFileVisible" width="40%">
      <el-form :model="addFileForm" :rules="addFileRules" ref="addFileFormRef" label-width="100px" class="demo-ruleForm">
        <el-form-item label="目录名称" prop="filename">
          <el-input v-model="addFileForm.filename"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addFileVisible = false">取 消</el-button>
        <el-button type="primary" @click="addFile">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改对话框 -->
    <el-dialog title="修改目录" :visible.sync="alterFileVisible" width="40%">
      <el-form :model="alterFileForm" :rules="alterFileRules" ref="addFileFormRef" label-width="100px"
        class="demo-ruleForm">
        <el-form-item label="原来的名称">
          <el-input readonly v-model="alterFileForm.oldname" placeholder="alterFileForm.oldname"></el-input>
        </el-form-item>

        <el-form-item label="修改后的名称">
          <el-input v-model="alterFileForm.newname"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="alterFileVisible = false">取 消</el-button>
        <el-button type="primary" @click="alterFile">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      queryInfo: {
        query: '',
        pagenum: 1, // 默认当前是第一页
        pagesize: 5, // 当前每页的数据是5条
        basepath: '/' + window.sessionStorage.getItem('token'),
        total: 0 // 总数默认为0
      },
      fileList: [],
      filePathList: [
        {
          basePath: window.sessionStorage.getItem('token')
        }
      ],
      searchfilename: '',
      filepath: '/' + window.sessionStorage.getItem('token'),
      total: 0,
      addFileVisible: false,
      addFileForm: {
        filename: ''
      },
      addFileRules: {
        filename: [
          {
            required: true,
            message: '请输入目录名',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 10,
            message: '长度在 3 到 10 个字符',
            trigger: 'blur'
          }
        ]
      },

      alterFileVisible: false,
      alterFileForm: {
        oldname: '',
        newname: '',
        filepath: ''
      },
      alterFileRules: {
        oldname: [
          {
            required: true,
            message: '请输入目录名',
            trigger: 'blur'
          },
          {
            min: 1,
            max: 10,
            message: '长度在 3 到 10 个字符',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  created() { // 页面加载之前的执行的函数
    // this.getUserList()
    this.sonFileList()
  },
  methods: {

    // 加载列表数据
    // async getUserList() {
    //   const { data: res } = await this.$http.get('lists', {
    //     params: this.queryInfo
    //   })

    //   if (res.code != 200) return this.$message.error('获取用户列表失败')
    //   // 赋值  后端返回给前端的数据
    //   this.fileList = res.data.slice(
    //     (this.queryInfo.pagenum - 1) * this.queryInfo.pagesize,
    //     this.queryInfo.pagesize * this.queryInfo.pagenum)
    //   this.total = res.total

    // },
    // 每页数据数
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize
      //this.getUserList(this.filepath)
      this.sonFileList(this.filepath);
    },
    // 点击第几页
    handleCurrentChange(newPage) {
      this.queryInfo.pagenum = newPage
      // this.getUserList(this.filepath)
      this.sonFileList(this.filepath);
    },
    // 创建目录
    async addFile() {
      console.log(this.addFileForm.filename)
      this.addFileVisible = false
      const { data: res } = await this.$http.get(
        'addFile?filename=' + this.filepath + '/' + this.addFileForm.filename // /用户名/xxx
      )
      this.sonFileList(this.filepath)
      if (res.code != 200) return this.$message.error(res.msg)
      this.$message.success(res.msg)
    },
    // 删除
    deleteFile(deleteFilepath) {
      const that = this
      this.$confirm('此操作将删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          that.$http
            .get('deleteFiles?deleteFilepath=' + deleteFilepath)
            .then(function (res) {
              that.sonFileList(that.filepath)
            })

          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    // 查询子路径 二级目录
    async sonFileList(filePath) {
      //console.log(filePath);
      if (filePath == null) {
        filePath = this.filepath;
      }
      //console.log(filePath);
      const { data: res } = await this.$http.get(
        'searchFiles?filePath=' + filePath
      )
      this.filepath = filePath
      //console.log(this.filepath);

      if (res.code != 200) return this.$message.error('获取用户列表失败')
      // 赋值 这一部就是前端拿到后端传过来的data 并赋值给前端声明出来的filelist
      //console.log(res.data)
      this.fileList = res.data.slice(
        (this.queryInfo.pagenum - 1) * this.queryInfo.pagesize,
        this.queryInfo.pagenum * this.queryInfo.pagesize
      );
      this.filePathList = res.path
      this.total = res.total
    },
    // 修改
    alterFile() {
      const that = this
      this.alterFileVisible = false
      //
      this.$http
        .get('alterFiles', {
          params: this.alterFileForm
        })
        .then(function (res) {
          that.sonFileList(that.filepath)
          that.$message.success('修改成功！！！')
        })
    },
    alterFile22(filename) {
      this.alterFileVisible = true
      this.alterFileForm.oldname = filename
      this.alterFileForm.filepath = this.filepath
    },
    // 上传
    uploadFile(item) {
      const that = this
      const formData = new FormData()
      const file = item.raw
      formData.append('file', file)// 上传文件的相关信息
      formData.append('filepath', this.filepath)// 上传在什么路径下
      this.$http({
        url: 'upload', // 后端提供的接口
        method: 'post',
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(function (res) {
        console.log(res.msg)
        if (res.code != 200) return this.$message.error('上传失败')// this.$message.error(res.msg)
        this.$message.success('上传成功')
        that.sonFileList(that.filepath)
      })
    },
    // 下载
    downLoadFile(filepath) {
      this.$http.get('downLoadFile?filePath=' + filepath).then(function (res) {
        window.open(res.data)
      })
    },
    // 搜索
    searchFiles() {
      const that = this
      console.log(this.searchfilename)
      if (this.searchfilename == "") {
        this.sonFileList(this.filepath);
      }
      this.$http
        .get('searchFiles22?searchFilename=' + this.searchfilename + '&searchFilepath=' + this.filepath)
        .then(function (res) {
          console.log(res)
          that.fileList = res.data.data
          that.total = res.data.total
        })
    },
    // 收藏
    collectionFile(filepath) {
      this.$http.get('collectFiles?filepath=' + filepath)
      this.$message.success('收藏成功！！！')
    }
  }
}
</script>

<style lang="less" scoped></style>
