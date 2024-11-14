"""
Standard insertion sort using list (stable)

"""

# insertion sort numbers in increasing order
def insertion_sort_1(lst):
    n = len(lst)
    
    for i in range(1, n):
        tmp = lst[i]
        for j in range(i-1, -1, -1):
            if lst[j] > tmp:
                lst[j+1] = lst[j]
                lst[j] = tmp
            else:
                break
          
def insertion_sort_2(the_list): 
    n = len(the_list)
    
    for mark in range(1,n):
        temp = the_list[mark]
        i = mark - 1
        
    while i >= 0 and the_list[i] > temp:
        the_list[i+1] = the_list[i]
        i-= 1
        
    the_list[i+1] = temp  


if __name__ == '__main__':
    the_list = [3,2,43,1,2,4,2,3,4,2,6,87,9,9,34,54,3]
    print(the_list)
    insertion_sort_1(the_list)
    print(the_list)
    


