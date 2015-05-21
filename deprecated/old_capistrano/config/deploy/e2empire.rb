set :user, 'Eric_Vader'
set :domain, 'e2empire.com:2200'
role :server,  domain, :primary => true

set :applicationdir, "#{deploymentdir}/current"
set :executecommand, "#{executecommandp1}#{executecommandp2}"
set :grepstring, "#{executable}"
set :pidname, "devel.pid"
set :servertype, "development"
