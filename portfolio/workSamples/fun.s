# Author: Joseph Randall Hunt
# Version: 2/20/10
#-------- Data Segment ---------
    .data
prompt:             .asciiz "Please enter a string > "
test1:              .asciiz "Test 1:\nThe length of the string \""
test2:              .asciiz "Test 2:\nThe new String is: "
test3:              .asciiz "Test 3:\nFive: "
iscolonspace:       .asciiz "\" is: "
sixcolonspace:      .asciiz "\nSix: "
sevencolonspace:    .asciiz "\nSeven: "
eightcolonspace:    .asciiz "\nEight: "
string:             .space 256
stringcopy:         .space 256
nl:                 .byte 10 0
#-------- Text Segment ---------

########## Nicely done: 15.9/16
    .text
    .globl main
main:
    li $v0, 4                   # Print prompt
    la $a0, prompt
    syscall

    li $v0, 8                   # Read string into string
    la $a0, string
    li $a1, 256
    syscall
    la $a0, nl                  # Print new line
    li $v0, 4
    syscall
    ###########################################################################
    ######## Registers #########
    ##      s0 = strlen       ##
    ##      s1 = straddr      ##
    ##      s2 = string[-1]   ##
    ##      s3 = compare      ##
    ##                        ##
    ############################
    li $v0, 4                   # Print "Test 1:\n..."
    la $a0, test1
    syscall

    la $a0, string              # Load the address of the string as an argument
    li $a1, 256                 # Load the length of the array as an argument
    jal strlen
    move $s0, $v0               # Move the return of strlen into s0

    addi $s0, $s0, -1           # subtract one from length
    la $s1, string
    add $s1, $s1, $s0
    lb $s2, 0($s1)
    li $s3, 10
    bne $s3, $s2, skipremovenl
    sb $zero, 0($s1)    ## nice
skipremovenl:
    li $v0, 4                   # Print string
    la $a0, string
    syscall

    la $a0, iscolonspace        # Print "\" is: "
    li $v0, 4
    syscall

    move $a0, $s0
    li $v0, 1                   # Load print_int syscall
    syscall

    la $a0, nl                  # Print new line
    li $v0, 4
    syscall
    ###########################################################################
    la $a0, nl                  # Print new line
    li $v0, 4
    syscall

    li $v0, 4                   # Print "Test 2:..."
    la $a0, test2
    syscall

    la $a0, stringcopy
    la $a1, string
    move $a3, $s0               # set max to strlen-1 (so no new line)
    jal strncpy                 # jump to strncpy

    la $a0, stringcopy          # Print the copied string
    li $v0, 4
    syscall

    la $a0, nl                  # Print new line
    li $v0, 4
    syscall
    ###########################################################################
    la $a0, nl                  # Print new line
    li $v0, 4
    syscall
    li $v0, 4                   # Print "Test 3: "
    la $a0, test3
    syscall
    addi $a0, $zero, 5
    jal mem_align
    move $a0, $v0
    li $v0, 1
    syscall

    la $a0, sixcolonspace
    li $v0, 4
    syscall
    addi $a0, $zero, 6
    jal mem_align
    move $a0, $v0
    li $v0, 1
    syscall

    la $a0, sevencolonspace
    li $v0, 4
    syscall
    addi $a0, $zero, 7
    jal mem_align
    move $a0, $v0
    li $v0, 1
    syscall

    la $a0, eightcolonspace
    li $v0, 4
    syscall
    addi $a0, $zero, 8
    jal mem_align
    move $a0, $v0
    li $v0, 1
    syscall

    la $a0, nl                  # Print new line
    li $v0, 4
    syscall
    j exit
    ###########################################################################
strlen:
    #------- Registers -------#
    #       s0=string[i]      #
    #-------------------------#

    addi $sp, $sp, -4           # $sp--; /* allocate 1 word */
    sw $s0, 0($sp)              # *($sp + 0) = $s0 /* save $s0 */
    add $s0, $zero, $zero       # clear s0
    add $v0, $zero, $zero       # clear v0
strlenloop:
######## line wrap (-.1)
    lb $s0, 0($a0)              # $s0 = string[i] //Q: could somehow move out of loop?
    beq $s0, $zero, strlenend   # exit if byte is null character
    addi $v0, $v0, 1            # length++
    addi $a0, $a0, 1            # i++... //Q: is there a way to only add once?
    ble $v0, $a1, strlenloop    # loop if neccesary
strlenend:
    lw $s0, 0($sp)              # $s1 = *($sp + 0); /* restore $s1 */
    addi $sp, $sp, 4            # $sp++; /* deallocate 1 words */
    jr $ra                      # return;
    ###########################################################################

strncpy:
    #------- Registers -------#
    #   s0=i                  #
    #   s1=&string[i]         #
    #   s2=string[i]          #
    #   s3=&strincopy[i]      #
    #-------------------------#
    addi $sp, $sp, -16          # $sp-=4; /* allocate 4 words */
    sw $s0, 0($sp)              # *($sp + 0) = $s0 /* save $s0 */
    sw $s1, 4($sp)              # *($sp + 4) = $s1 /* save $s1 */
    sw $s2, 8($sp)              # *($sp + 8) = $s2 /* save $s2 */
    sw $s3, 12($sp)             # *($sp + 12) = $s3 /* save $s3 */
    add $s0, $zero, $zero       # Clear all saved registers
    add $s1, $zero, $zero
    add $s2, $zero, $zero
    add $s3, $zero, $zero
L1:
    add $s1,$s0,$a1             # $s1 = &string[i] in $s1
    lb $s2, 0($s1)              # $s2 = string[i]
    add $s3,$s0,$a0             # $s3 = &stringcopy[i]
    sb $s2, 0($s3)              # stringcopy[i] = string[i]
    beq $s2, $zero, strncpyend  # if (string[i] == 0) { goto strncpyend }
    bge $s0, $a3, strncpyend    # if (i >= max)
    addi $s0, $s0, 1            # i++
    j L1                        # loop

strncpyend:
    lw $s0, 0($sp)              # $s3 = *($sp + 0); /* restore $s0 */
    lw $s1, 4($sp)              # $s3 = *($sp + 4); /* restore $s1 */
    lw $s2, 8($sp)              # $s3 = *($sp + 8); /* restore $s2 */
    lw $s3, 12($sp)             # $s3 = *($sp + 12); /* restore $s3 */
    addi $sp, $sp, 16           # $sp+=4; /* deallocate 4 words */
    jr $ra                      # return;
    ###########################################################################

mem_align:
    #------- Registers -------#
    #   s0=quorem             #
    #   s1=rem                #
    #-------------------------#
    addi $sp, $sp, -4
    sw $s0, 0($sp)
    sw $s1, 4($sp)
    addi $s0, $zero, 4          # clear s0 then put 4 in it
    add $v0, $zero, $zero       # clear v0
    div $a0, $s0                # divide argument by 4
    mfhi $s1                    # get remainder and put it in s1
    beq $s1, $zero, remis0      # if s1 == 0 set v0 to 4 otherwise:
    move $v0, $s1               # ret = rem
remret:
    lw $s0, 0($sp)              # dealloc
    lw $s1, 4($sp)              # dealloc
    addi $sp, $sp, 4            # dealloc
    jr $ra
remis0:
    li $v0, 4
    j remret
    ###########################################################################
exit:
    li $v0, 10
    syscall

