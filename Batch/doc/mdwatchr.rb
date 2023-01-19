
watch( "(.*).md" ) { |md|

  file = File.basename(md[0], ".md")
  puts file

  puts `rake -f #{Dir.pwd}/Rakefile #{file}`
#  puts "rake -f #{Dir.pwd}/Rakefile #{file}"

#  system "gdoc"
  puts "\n"
}

