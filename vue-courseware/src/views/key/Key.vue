<template>
    <div class="key">
        <el-button type="success" @click="show=true" plain>添加密钥</el-button>
        <el-table
                :data="tableData.list"
                style="width: 100%">
            <el-table-column label="课件id" width="180">
                <template slot-scope="scope">
                    {{scope.row.cwId}}
                </template>
            </el-table-column>
            <el-table-column label="key" width="180">
                <template slot-scope="scope">
                    <el-tag size="medium" type="warning">{{ scope.row.exKey }}</el-tag>
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
                    label="使用日期"
                    width="180">
                <template slot-scope="scope">
                    <i class="el-icon-time"></i>
                    <span style="margin-left: 10px">{{ scope.row.useTime }}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="是否被使用"
                    width="180">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.isUsed }}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="使用者id"
                    width="180">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.userId}}</span>
                </template>
            </el-table-column>
            <el-table-column label="操作" fixed="right" width="180">
                <template slot-scope="scope">
                    <el-button type="danger" icon="el-icon-delete" @click="handleDelete(scope.$index, scope.row)"
                               circle></el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
                background
                layout="prev, pager, next"
                :page-count="tableData.pages"
                :current-page.sync="currentPage"
                @current-change="list">
        </el-pagination>
        <el-dialog
                title="生成密钥"
                :visible.sync="show"
                width="50%" class="dialog">
            <el-form ref="form" :model="exKey" label-width="80px">
                <el-form-item label="课件id" label-position="left">
                    <el-input class="input price" placeholder="请输入课件id" v-model="exKey.cwId"></el-input>
                </el-form-item>
                <div class="submit">
                    <el-button type="success" @click="add" plain>生成密钥</el-button>
                </div>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "Key",
        data() {
            return {
                show: false,
                exKey: {},
                currentPage: 1,
                tableData: {}
            }
        },
        mounted() {
            this.list()
        },
        methods: {
            add() {
                this.$http.get("/cw-exchange-key/add", {params: {id: this.exKey.cwId}}).then(res => {
                    if (res.data.data == true) {
                        this.show = false
                        this.$message.success("添加成功")
                    } else {
                        this.$message.error("添加失败,请检查课件id是否正确")
                    }
                })
            },
            handleDelete(index, row) {
                this.$http.get("/cw-exchange-key/delete", {params: {id: row.id}}).then((res) => {
                    if (res.data.data == true) {
                        this.$message.success("删除成功")
                    } else {
                        this.$message.error("删除失败")
                    }
                })
            },
            list() {
                this.$http.get("/cw-exchange-key/list", {params: {start: this.currentPage}}).then(res => {
                    this.tableData = res.data.data
                })
            }
        }
    }
</script>

<style lang="scss" scoped>
    .key {
        margin: 20px;
        padding: 20px;
        border-radius: 10px;
        background-color: rgba(232, 232, 232, 0.53);
    }
</style>