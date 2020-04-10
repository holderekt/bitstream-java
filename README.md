# BitStream
Very, very simple library for handling bit livel IO operations. Reads and writes bits with big endian notation. 
Missing bit in write operations are padded with zeros
### Use example
Read
```
File: 10010101 00011001 00010110

read bit  = 1
read bit  = 0
read byte = 01010100
read byte = 01100100

```
Write
```
write bit 0
write bit 1
write bit 1
write byte 01101110

File: 01101101 11000000
```
