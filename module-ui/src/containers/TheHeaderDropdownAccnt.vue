<template>
  <CDropdown
          inNav
          class="c-header-nav-items"
          placement="bottom-end"
          add-menu-classes="pt-0"
  >
    <template #toggler>
      <CHeaderNavLink>
        <div class="c-avatar">
          <img
                  src="img/avatars/6.jpg"
                  class="c-avatar-img "
          />
        </div>
      </CHeaderNavLink>
    </template>
    <CDropdownHeader tag="div" class="text-center" color="light">
      <strong>Account</strong>
    </CDropdownHeader>
    <CDropdownItem>
      <CIcon name="cil-user" /> Profile
    </CDropdownItem>
    <CDropdownItem>
      <CIcon name="cil-file" /> Projects
      <CBadge color="primary" class="ml-auto">{{ itemsCount }}</CBadge>
    </CDropdownItem>
    <CDropdownDivider/>
    <CDropdownItem v-on:click="logout()">
      <CIcon name="cil-lock-locked" /> Logout
    </CDropdownItem>
  </CDropdown>
</template>

<script>
  import router from './../router'

  export default {
    name: 'TheHeaderDropdownAccnt',
    data () {
      return {
        itemsCount: 42
      }
    },
    methods: {
      logout() {
        router.push('/login');
      }
    }
  }
</script>

<style scoped>
  .c-icon {
    margin-right: 0.3rem;
  }
</style>
