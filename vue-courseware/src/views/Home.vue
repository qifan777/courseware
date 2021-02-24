<template>
    <div class="welcome">
        <el-container>
            <el-header style="text-align: right; font-size: 12px">
            </el-header>
            <el-container class="mainContent">
                <el-aside>
                    <el-menu default-active="1" class="el-menu-vertical-demo"
                             :router="true">
                        <el-menu-item index="1" :route="'/courseware'">
                            <template slot="title">
                                <i class="el-icon-data-analysis" style="color: white"></i>
                                <span class="title" slot="title">课件管理</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="2" :route="'/key'">
                            <template slot="title">
                                <i class="el-icon-key" style="color: white"></i>
                                <span class="title" slot="title"> 兑换密钥管理</span>
                            </template>
                        </el-menu-item>
                    </el-menu>
                </el-aside>
                <el-main>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    export default {
        name: "admin",
        mounted() {
            this.$store.dispatch('refreshUserInfo')
        }
    }
</script>

<style lang="scss" scoped>
    .el-menu {
        background-color: #272c33;

        .title {
            color: #f4f4f4;
        }

        .el-menu-item {
            color: #2b333f;
        }
    }

    .el-header {
        background-color: #2b333f;
    }

    .el-main {
        padding-top: 0;
    }
</style>