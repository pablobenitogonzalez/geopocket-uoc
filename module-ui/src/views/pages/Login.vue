<template>
  <CContainer class="d-flex align-items-center min-vh-100">
    <CRow class="justify-content-center">
      <CCol md="8">
        <CCardGroup>
          <CCard color="primary"
                 text-color="white"
                 class="text-center py-5 d-md-down-none"
                 style="width:44%"
                 body-wrapper
          >
            <h2>GeoPocket</h2>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
            <p>
              <i>student1@uoc.edu</i> - <i>123456</i><br>
              <i>student2@uoc.edu</i> - <i>123456</i><br>
              <i>professor@uoc.edu</i> - <i>123456</i></p>
          </CCard>
          <CCard class="p-4">
            <CCardBody>
              <CForm>
                <h1>Login</h1>
                <p class="text-muted">Sign In to your account</p>
                <CInput
                        placeholder="Username"
                        type="text"
                        description="Please enter your username."
                        autocomplete="username"
                        v-model="username"
                        :was-validated="usernameWasValidated"
                        @update:value="onChangeUsername"
                        v-on:keyup.enter="login"
                >
                  <template #prepend-content><CIcon name="cil-user"/></template>
                </CInput>
                <CInput
                        ref="password"
                        placeholder="Password"
                        type="password"
                        description="Please enter your password."
                        autocomplete="current-password"
                        v-model="password"
                        :was-validated="passwordWasValidated"
                        @update:value="onChangePassword"
                        v-on:keyup.enter="login"
                >
                  <template #prepend-content><CIcon name="cil-lock-locked"/></template>
                </CInput>
                <CRow>
                  <CCol col="7" class="text-left">
                    <div v-if="error" class="alert alert-danger" style="margin-bottom: 0; padding: 6px; text-align: center;">{{error}}</div>
                  </CCol>
                  <CCol col="5" class="text-right">
                    <CButton color="primary" class="px-4" :disabled="!username || !password || loading" v-on:click="login">Login</CButton>
                  </CCol>
                </CRow>
              </CForm>
            </CCardBody>
          </CCard>
        </CCardGroup>
      </CCol>
    </CRow>
  </CContainer>
</template>

<script>
  import router from './../../router'
  import {RepositoryFactory} from './../../repositories/RepositoryFactory'

  const SecurityRepository = RepositoryFactory.get('security');
  export default {
    name: 'Login',
    data () {
      return {
        username: '',
        usernameWasValidated: false,
        password: '',
        passwordWasValidated: false,
        loading: false,
        error: ''
      }
    },
    created () {
      // reset login status
      SecurityRepository.logout();

      // get return url from route parameters or default to '/'
      this.returnUrl = this.$route.query.returnUrl || '/';
    },
    methods: {
      login () {
        const { username, password } = this;

        // stop here if form is invalid
        if (!(username && password)) {
          return;
        }

        this.loading = true;
        SecurityRepository.login(username, password)
                .then(
                        user => router.push(this.returnUrl),
                        error => {
                          this.error = error.response.data;
                          this.username = '';
                          this.usernameWasValidated = false;
                          this.password = '';
                          this.passwordWasValidated = false;
                          this.loading = false;
                        }
                );
      },
      onChangeUsername (value) {
        this.error = '';
        this.usernameWasValidated = !!value;
      },
      onChangePassword (value) {
        this.error = '';
        this.passwordWasValidated = !!value;
      }
    }
  }
</script>
