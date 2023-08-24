layout 'layouts/main.tpl',
pageTitle: 'List users',
mainBody:
    contents
    {
        ul
        {
            users.each
            {
                user -> li
                {
                    a(href:"http://localhost:8080/$user.id", "$user.lastName $user.firstName")
                }
            }
        }
        div
        {
            a(href:"http://localhost:8080/add", 'Add new user')
        }
    }