# Author: Han Liang Wee Eric
# (c) IHPC, A*STAR 2013

require 'capistrano/ext/multistage'

# stage settings
set :stages, ["development","e2empire","ec2"]
set :default_stage, "development"

# application settings
set :deploymentdir, "/opt/scl-crawler"
set :version, "0.0.1-SNAPSHOT"
set :executable, "ShoppingMallDriver"

# git settings
set :scm, 'git'
set :repository,  "git@bitbucket.org:duanrb/scl-crawler.git"
set :gituser, "Eric Han"
set :gitemail, "Eric.aka.darth_vader@hotmail.com"
set :branch, 'master'
set :git_shallow_clone, 1
set :scm_verbose, true
set :deploy_via, :remote_cache
set :keep_releases, 50
set :copy_exclude, [ '.git' ]

# servers roles
# Note from author: when you want to add additional servers, please becareful as this script is not thread safe,
# and all the commands are run in parallel.
# Server settings are in the individual scripts

# command
set :executecommandp1, "(nohup java -server -cp lib/*:target/scl-crawler-#{version}.jar:properties #{executable} "
set :executecommandp2, ' > /dev/null < /dev/null 2>&1 &) && sleep 5'

# additional settings
default_run_options[:pty] = true
set :use_sudo, false
# deploy config
set :deploy_to, deploymentdir
set :deploy_via, :export

def remote_file_exists?(full_path) 
	results = []

	invoke_command("if [ -e #{full_path} ]; then echo -n 'true'; fi") do |ch, stream, out|
		results << (out == 'true')
	end
	return results.include?(true)
end

def printline()
	puts "====================================================================================="
end

desc "list versions"
task :listversion do
	run "cd #{deploymentdir}/releases/; ls -l"
end

desc "Configure Git settings and prepare the directory"
task :prepare do
	run "git config --global user.name \"#{gituser}\""
	run "git config --global user.email \"#{gitemail}\""
	run "sudo mkdir #{deploymentdir}"
	run "sudo chown #{user}:#{user} #{deploymentdir}"
	deploy.setup
end

desc "Check status of the Server"
task :status do
	statusihpc
end

def statuscode(ipaddress)
	if remote_file_exists?("#{deploymentdir}/shared/pids/#{pidname}")
		currentpid = capture("cd #{deploymentdir}/shared/pids; cat #{pidname}")
		laststarted = capture("find #{deploymentdir}/shared/pids -name #{pidname} -printf '%c'")
		
		printline()
		puts "SCL-Crawler@#{ipaddress}(#{servertype}) - Sustainable City Life Crawler"
		puts "   Active: \e[1;32mactive (running)\e[0m since #{laststarted}"
		puts " Main PID: #{currentpid}"
		printline()
	else
		printline()
		puts "SCL-Crawler@#{ipaddress}(#{servertype}) - Sustainable City Life Crawler"
		puts "   Active: \e[1;31minactive (dead)\e[0m"
		printline()
	end
end

task :statusihpc, :roles =>:server do
	statuscode("#{domain}")
end
