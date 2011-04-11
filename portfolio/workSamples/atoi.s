#Joseph Randall Hunt
#8/2/10
# without shifts or ands
# t0, address of buf
# t1, the individual bytes of buf
# t2, 48
# t3, int2ascii
    .data
str: .asciiz "Please enter an integer > "
buf: .byte 0 0 0 0 0     # buffer for string, 4 values + nl
nl:  .byte 10 0         # newline, null
scs: .byte 32 58 32 0   # space colon space null
int2ascii: .byte 48     # add to int to get a char
    .text 
main:
    li $v0, 4           # Print string syscall
    la $a0, str         # load argument for syscall
    syscall

    li $a0, 0           # clear a0

    li $v0, 8           # load read string syscall
    la $a0, buf         # give the address of buf to a0
    la $a1, 5           # give the length of buf+null to a1
    syscall
    ######### First Int #########
    la $t0, buf         # load the address of buf into a0
    lb $t1, ($t0)       # load the first byte of buf into t1

    li $v0, 1           # print_int syscall
    la $a0, ($t1)       # load address of int stored in t1 into a0
    syscall

    la $t3, int2ascii   # load address of 48 into t3
    lb $t2, ($t3)       # put 48 into t2
    sub $t1, $t1, $t2   # subtract 48 from char value to get int value

    li $v0, 4
    la $a0, scs         # print space colon space
    syscall

    li $v0, 1           # Print int syscall
    la $a0, ($t1)       # Print the string
    syscall
    
    li $v0, 4
    la $a0, nl          # print new line
    syscall
    ######## Second Int #########
    lb $t1, 1($t0) #load second byte of buf into t1
    li $v0, 1
    la $a0, ($t1)
    syscall
    sub $t1, $t1, $t2
    li $v0, 4
    la $a0, scs
    syscall
    li $v0, 1
    la $a0, ($t1)
    syscall
    li $v0, 4
    la $a0, nl
    syscall
    ######### Third Int #########
    lb $t1, 2($t0)      # load the third byte of buf into t1
    li $v0, 1
    la $a0, ($t1)
    syscall
    sub $t1, $t1, $t2
    li $v0, 4
    la $a0, scs
    syscall
    li $v0, 1
    la $a0, ($t1)
    syscall
    li $v0, 4
    la $a0, nl
    syscall
    ######### Last Int ##########
    lb $t1, 3($t0)  # load the fourth byte of buf into t1
    li $v0, 1
    la $a0, ($t1)
    syscall
    sub $t1, $t1, $t2
    li $v0, 4
    la $a0, scs
    syscall
    li $v0, 1
    la $a0, ($t1)
    syscall
    li $v0, 4
    la $a0, nl
    syscall
    ######## EXIT ########
    li $v0, 10          # Exit
    syscall
