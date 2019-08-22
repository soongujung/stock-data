import sys
import subprocess as sub_process

print(sys.platform)
os_type = sys.platform

if os_type.find('win') >= 0:
    os_type = 'window'
elif os_type.find('linux') >= 0:
    os_type = 'linux'
else:
    os_type = 'linux'

if os_type == 'window':
    result = sub_process.run("dir", shell=True)
elif os_type == 'linux':
    result = sub_process.run("ls -al", shell=True)

# result = sub_process.run("")
print("result >>> {}".format(result))

