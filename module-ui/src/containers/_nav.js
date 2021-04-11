export default [
  {
    _name: 'CSidebarNav',
    _children: [
      {
        _name: 'CSidebarNavItem',
        name: 'Dashboard',
        to: '/dashboard',
        icon: 'cil-layers'
      },
      {
        _name: 'CSidebarNavDropdown',
        name: 'Projects',
        route: '/project',
        icon: 'cil-folder',
        items: [
          {
            name: 'Manage Projects',
            to: '/project/manage',
            icon: 'cil-settings'
          },
          {
            name: 'Create New Project',
            to: '/project/create',
            icon: 'cil-plus'
          }
        ]
      },
      {
        _name: 'CSidebarNavTitle',
        _children: ['Applications']
      },
      {
        _name: 'CSidebarNavDropdown',
        name: 'Liquec',
        route: '/liquec',
        icon: 'cil-applications',
        items: [
          {
            name: 'Manage Calculations',
            to: '/liquec/calculations',
            icon: 'cil-settings'
          },
          {
            name: 'Manage Drafts',
            to: '/liquec/drafts',
            icon: 'cil-settings'
          },
          {
            name: 'Create New Liquec',
            to: '/liquec/create',
            icon: 'cil-plus'
          }
        ]
      }
    ]
  }
]
