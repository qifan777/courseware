<template>
  <div class="main">
    <div class="btns">
      <el-button type="success" @click="show=true" plain>添加课件</el-button>
      <el-button type="primary" @click="loadCarousel" plain>显示轮播图</el-button>
    </div>
    <el-table
        :data="tableData.list"
        style="width: 100%">
      <el-table-column label="id" width="180">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column label="标题" width="180">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="封面" width="180">
        <template slot-scope="scope">
          <img class="cover" :src="getImgPath(scope.row.cover)" v-if="scope.row.cover"/>
        </template>
      </el-table-column>
      <el-table-column label="轮播封面" width="180">
        <template slot-scope="scope">
          <img class="cover" :src="getImgPath(scope.row.carouselUrl)" v-if="scope.row.carouselUrl"/>
        </template>
      </el-table-column>
      <el-table-column label="价格" width="180">
        <template slot-scope="scope">
          <el-tag size="medium" type="warning">{{ '￥' + scope.row.price }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          label="创建日期"
          width="180">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column
          label="下载地址"
          width="180">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top" v-if="scope.row.url">
            <p>下载地址:{{ getImgPath(scope.row.url, false, 1) }}</p>
            <div slot="reference" class="name-wrapper">
              {{ getImgPath(scope.row.url, false, 1).substring(0, 20) + '......' }}
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
          label="销售量"
          width="180">
        <template slot-scope="scope">
          <i class="el-icon-download"></i>
          <span style="margin-left: 10px">{{ scope.row.count }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="180">
        <template slot-scope="scope">
          <div class="operations">
            <div class="operations-btn">
              <el-button type="primary" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)"
                         circle></el-button>
              <el-button type="danger" icon="el-icon-delete" @click="handleDelete(scope.$index, scope.row)"
                         circle></el-button>
            </div>
            <div class="operations-select">
              <el-select v-model="scope.row.isCarousel" placeholder="请选择"
                         @change="handleCarousel(scope.$index,scope.row)">
                <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </div>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        background
        layout="prev, pager, next"
        :page-count="tableData.totalPage"
        :current-page.sync="currentPage"
        @current-change="loadData">
    </el-pagination>
    <el-dialog
        title="添加课件"
        :visible.sync="show"
        width="50%" class="dialog">
      <el-form ref="form" :model="courseware" label-width="150px">
        <el-form-item label="标题" label-position="left">
          <el-input class="input" placeholder="请输入标题" v-model="courseware.name"></el-input>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="价格" label-position="left">
          <el-input class="input price" placeholder="格式:12.99" v-model="courseware.price"></el-input>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item>
          <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :file-list="carouselList"
              :http-request="uploadCarousel"
              :limit="1"
              list-type="picture">
            <el-button size="small" type="primary">上传轮播图封面(只有✔时生效)</el-button>
          </el-upload>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item>
          <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :file-list="imageList"
              :before-upload="beforeCoverUpload"
              :limit="1"
              :http-request="coverUpload"
              list-type="picture">
            <el-button size="small" type="primary">上传封面</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2mb</div>
          </el-upload>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item>
          <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :http-request="uploadFile"
              :on-remove="handleFileRemove"
              :file-list="fileList">
            <el-button size="small" type="primary">上传课件</el-button>
          </el-upload>
        </el-form-item>
        <div class="submit">
          <el-button type="success" @click="courseUpload" plain>提交</el-button>
        </div>
      </el-form>
    </el-dialog>
    <el-dialog
        title="修改课件信息"
        :visible.sync="editShow"
        width="50%" class="dialog">
      <el-form ref="form" :model="courseware" label-width="80px">
        <el-form-item label="标题" label-position="left">
          <el-input class="input" placeholder="请输入标题" v-model="courseware.name"></el-input>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="价格" label-position="left">
          <el-input class="input price" placeholder="格式:12.99" v-model="courseware.price"></el-input>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item>
          <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :file-list="carouselList"
              :http-request="uploadCarousel"
              :limit="1"
              list-type="picture">
            <el-button size="small" type="primary">上传轮播图封面(只有✔时生效)</el-button>
          </el-upload>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item>
          <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :file-list="imageList"
              :before-upload="beforeCoverUpload"
              :http-request="coverUpload"
              :limit="1"
              list-type="picture">
            <el-button size="small" type="primary">上传封面</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2mb</div>
          </el-upload>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item>
          <el-upload
              class="upload-demo"
              action="https://jsonplaceholder.typicode.com/posts/"
              :http-request="uploadFile"
              :on-remove="handleFileRemove"
              :file-list="fileList">
            <el-button size="small" type="primary">上传课件</el-button>
          </el-upload>
        </el-form-item>
        <div class="submit">
          <el-button type="success" @click="courseUpdate" plain>提交</el-button>
        </div>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {uploadFile} from "../../api/api";
import {getImgPath} from "../../util/common";

export default {
  mixins: [getImgPath],
  data() {
    return {
      tableData: {
        totalPage: 1
      },
      courseware: {},
      show: false,
      editShow: false,
      imageList: [],
      fileList: [],
      carouselList: [],
      currentPage: 1,
      fileUrlList: [],
      options: [{value: 0, label: 0}, {value: 1, label: 1}, {value: 2, label: 2}, {
        value: 3,
        label: 3
      }, {value: 4, label: 4}, {value: 5, label: 5}]
    }
  },
  mounted() {
    this.loadData()
  },
  methods: {
    //单击页数加载对应的页面
    loadData() {
      this.$http.get("/cw-courseware/listForAdmin", {params: {start: this.currentPage}}).then((res) => {
        this.tableData = res.data.data
      })
    },
    //设置轮播图的顺序 0代表不是轮播图,1,2,3,4,5代表轮播的顺序
    handleCarousel(index, row) {
      this.courseware = row
      this.courseUpdate()
    },
    //点击编辑按钮
    handleEdit(index, row) {
      //当前编辑的课件文件夹
      this.courseware = row
      this.editShow = true
      this.carouselList = []
      this.imageList = []
      //预览当前编辑课件的封面
      this.imageList.push({name: row.name, url: this.getImgPath(row.cover)})
      //预览当前编辑课件的文件内容
      this.fileList = []
      this.fileUrlList = []
      const splits = row.url.split(";")
      splits.forEach((value, index1) => {
        let filename = value.split("/")
        this.fileUrlList.push(value)
        this.fileList.push({name: filename[filename.length - 1], url: value})
      })
    },
    //点击删除按钮
    handleDelete(index, row) {
      this.$http.get("/cw-courseware/delete", {params: {id: row.id}}).then((res) => {
        if (res.data.data && res.data.data == true) {
          this.$message.success("删除成功")
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    //加载轮播图
    loadCarousel() {
      this.$http.get("/cw-courseware/getCarouselForAdmin").then((res) => {
        this.tableData = {
          totalPage: 1,
          pageNum: 1,
          pageSize: 5,
          total: res.data.data.length,
          list: res.data.data
        }
      })
    },
    //覆盖element-ui的默认上传方式,上传轮播图。
    uploadCarousel(params) {
      let file = params.file
      const formData = new FormData()
      formData.append("file", file)
      uploadFile(formData).then((res) => {
        this.courseware.carouselUrl = res.data.data.url
        params.onSuccess()
      })
    },
    //覆盖element-ui的默认上传方式,上传课件。
    async uploadFile(params) {
      const file = params.file
      let filename = new Date().getTime() + file.name
      await this.$client.put(filename, file)
      await this.$client.putACL(filename, 'public-read');
      let url = '/resource/' + filename;
      params.file.url = url
      this.fileUrlList.push(url)
      params.onSuccess()
    },
    //当删除一个文件的时候,从当前的编辑对象中移除对应的url
    handleFileRemove(file, fileList) {
      this.fileUrlList = this.fileUrlList.filter(value => {
        return file.url != value
      })
    },
    //在上传之前校验文件的格式和大小
    beforeCoverUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isPng = file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG && !isPng) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return (isPng || isJPG) && isLt2M;
    },
    //覆盖element-ui的默认上传方式,上传封面。
    coverUpload(params) {
      let file = params.file
      const formData = new FormData()
      formData.append("file", file)
      uploadFile(formData).then((res) => {
        if (res.data.data.url) {
          params.onSuccess()
          this.courseware.cover = res.data.data.url
        }
      })
    },
    courseUpload() {
      if (this.courseware.url.length > 0 || this.fileUrlList.length > 0) {
        if (this.fileUrlList.length > 0) {
          this.courseware.url = this.fileUrlList.join(";")
        }
        this.$http.post("/cw-courseware/add", this.courseware).then((res) => {
          if (res.data.data && res.data.data === true) {
            this.$message({
              type: "success",
              message: "添加成功"
            })
            this.carouselList = []
            this.fileUrlList = []
            this.fileList = []
            this.imageList = []
            this.courseware = {}
            this.show = false
          } else {
            this.$message.error(res.data.data)
          }
        })
      } else {
        this.$message.error("请上传课件")
      }
    },
    courseUpdate() {
      if (this.courseware.url.length > 0 || this.fileUrlList.length > 0) {
        if (this.fileUrlList.length > 0) {
          this.courseware.url = this.fileUrlList.join(";")
        }
        this.$http.post("/cw-courseware/update", this.courseware).then((res) => {
          if (res.data.data && res.data.data == true) {
            this.$message.success("修改成功")
            this.carouselList = []
            this.fileUrlList = []
            this.fileList = []
            this.imageList = []
            this.courseware = {}
            this.editShow = false
          } else {
            this.$message.error("修改失败")
          }
        })
      } else {
        this.$message.error("请上传课件")
      }
    }
  }
}
</script>
<style lang="scss" scoped>


.main {
  margin: 20px;
  padding: 20px;
  border-radius: 10px;
  background-color: rgba(232, 232, 232, 0.53);

  .operations {
    display: flex;
    flex-direction: column;
    align-items: center;
    .operations-btn{
      margin-bottom: 5px;
    }
  }

  .btns {
    margin-bottom: 20px;
  }

  .cover {
    width: 100%;
    height: auto;
    max-height: 180px;
  }

  .price {
    width: 100px;
  }

  .submit {
    width: 100%;
    display: flex;
    justify-content: center;
  }
}
</style>
