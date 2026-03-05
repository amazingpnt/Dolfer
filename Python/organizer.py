import os
import sys
import shutil


if len(sys.argv)<2:
    folder=os.getcwd()
else: folder=sys.argv[1]

folder=os.path.abspath(folder)

for file in os.listdir(folder):
    file_path=os.path.join(folder, file)
    if os.path.isfile(file_path):
        ext=file.split(".")[-1]
        ext_folder=os.path.join(folder, ext)
        os.makedirs(ext_folder, exist_ok=True)
        shutil.move(file_path, os.path.join(ext_folder, file))




