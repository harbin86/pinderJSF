set :user, 'routemyday'
set :domain, '10.217.168.140'
role :server,  domain, :primary => true

set :applicationdir, "#{deploymentdir}/current"
set :executecommand, "#{executecommandp1}#{executecommandp2}"
set :grepstring, "#{executable}"
set :pidname, "devel.pid"
set :servertype, "development"
