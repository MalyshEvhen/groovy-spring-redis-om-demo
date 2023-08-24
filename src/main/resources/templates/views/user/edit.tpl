layout 'layouts/main.tpl',
pageTitle: 'Edit user',
mainBody:
    contents
    {
        div {
            p("ID: $user.id")
            p("First name: $user.firstName")
            p("Last name: $user.lastName")
            p("Email: $user.email")
            p("Bio: $user.bio")
            p("Roles: ")
            a
            {
                user.roles.each
                { role ->
                    li
                    {
                        a("$role")
                    }
                }
            }
        }
    }