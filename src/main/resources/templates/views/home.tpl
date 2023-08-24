layout 'layouts/main.tpl',
pageTitle: 'Museum Demo application',
mainBody:
    contents
    {
        div("This application on 99% made in groovy! It is fun)")
        {
            a( href: "http://localhost:8080/list", "Page of users" )
        }
    }