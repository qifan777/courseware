<template>
    <div>
        <div class="login" :style="'background-image:url('+Background+');'">
            <el-form ref="loginForm" :model="loginForm" :rules="loginRules" label-position="left" label-width="70px"
                     class="login-form">
                <h3 class="title">课件后台管理</h3>
                <el-form-item prop="username" label="账号">
                    <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
                    </el-input>
                </el-form-item>
                <el-form-item prop="password" label="密码">
                    <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="密码"
                              @keyup.enter.native="handleLogin">
                    </el-input>
                </el-form-item>
                <el-form-item style="width:100%;">
                    <el-button :loading="loading" size="medium" type="primary" style="width:100%;"
                               @click.native.prevent="handleLogin">
                        <span v-if="!loading">登 录</span>
                        <span v-else>登 录 中...</span>
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import Background from '@/assets/img/background.jpg'
    import axios from "axios";

    export default {
        name: "Login",
        data() {
            return {
                Background: Background,
                loginForm: {
                    username: '',
                    password: '',
                },
                loginRules: {
                    username: [{required: true, trigger: 'blur', message: '用户名不能为空'}],
                    password: [{required: true, trigger: 'blur', message: '密码不能为空'}]
                },
                loading: false,
            }
        },
        methods: {
            handleLogin() {
                this.$refs.loginForm.validate(valid => {
                    const user = {
                        username: this.loginForm.username,
                        password: this.$md5(this.loginForm.password),
                    }
                    if (valid) {
                        this.loading = true
                        this.$axios.post("/cw-api/user/login", user).then((res) => {
                            this.loading=false
                            if (res.data.data && res.data.data.token) {
                                this.$store.commit('setUserInfo', res.data.data)
                                this.$router.push({path: '/'})
                            }else {
                                this.$message.error(res.data.message)
                            }
                        })
                    } else {
                        return false
                    }
                })
            },
        }
    }
</script>

<style lang="scss" scoped>
    .login {
        width: 100%;
        height: 100vh;
        background-size: cover;
        display: flex;
        justify-content: center;
        align-items: center;

        .title {
            text-align: center;
            margin: 0 auto 30px auto;
            color: #707070;

        }

        .login-form {
            border-radius: 6px;
            background: #ffffff;
            width: 385px;
            padding: 25px 25px 5px 25px;

            .el-input {
                height: 38px;

                input {
                    height: 38px;
                }
            }

            .input-icon {
                height: 39px;
                width: 14px;
                margin-left: 2px;
            }
        }
    }
</style>