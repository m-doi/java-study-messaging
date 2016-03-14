# -*- mode: ruby -*-

VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.vm.box = "puphpet/centos65-x64"
  config.vm.network :private_network, ip: "192.168.200.22"
  config.vm.network :forwarded_port, guest: 5672, host: 5672
  config.vm.network :forwarded_port, guest: 15672, host: 15672

  config.vm.provision :shell, inline: <<-EOT
    yum update -y

    wget http://packages.erlang-solutions.com/erlang-solutions-1.0-1.noarch.rpm
    rpm -Uvh erlang-solutions-1.0-1.noarch.rpm
    yum -y install erlang

    gpg --keyserver pgp.mit.edu --recv-keys 0x056E8E56
    wget http://www.rabbitmq.com/releases/rabbitmq-server/v3.6.1/rabbitmq-server-3.6.1-1.noarch.rpm
    yum -y install rabbitmq-server-3.6.1-1.noarch.rpm

    service rabbitmq-server start
    chkconfig rabbitmq-server on

    rabbitmq-plugins enable rabbitmq_management
    rabbitmqctl add_user admin password
    rabbitmqctl set_user_tags admin administrator
    rabbitmqctl set_permissions -p / admin ".*" ".*" ".*"

  EOT
end
