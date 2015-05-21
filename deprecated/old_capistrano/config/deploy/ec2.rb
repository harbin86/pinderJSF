set :user, 'ubuntu'
set :domain, '54.251.162.243'
role :server,  domain, :primary => true
ssh_options[:forward_agent] = true
ssh_options[:keys] = ["#{ENV['HOME']}/.ssh/scl-crawl.pem","#{ENV['HOME']}/.ssh/bitbucket_rsa"] 

set :applicationdir, "#{deploymentdir}/current"
set :executecommand, "#{executecommandp1}#{executecommandp2}"
set :grepstring, "#{executable}"
set :pidname, "devel.pid"
set :servertype, "development"
