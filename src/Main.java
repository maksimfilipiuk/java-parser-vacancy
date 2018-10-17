/*
      Java vacancy parser (next. Java parser). Using the jsoup library.
      Copyright (c) 2018 by Maksim Filipiuk (devMax) <maksim.filipiuk@gmail.com>

      This program is free software: you can redistribute it and/or
      modify it under the terms of the GNU General Public License as
      published by the Free Software Foundation; either version 2 of
      the License, or (at your option) any later version.

      This program is distributed in the hope that it will be
      useful, but WITHOUT ANY WARRANTY; without even the implied
      warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
      PURPOSE. See the GNU General Public License for more details.

      You should have received a copy of the GNU General Public
      License along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Scanner;
import java.io.FileWriter;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter job title: ");
        String vacancy = in.nextLine();

        if(vacancy.contains("#")){
            vacancy = vacancy.replace("#", "%23");
        }
        /*
        else if(vacancy.contains("+")) {
            vacancy = vacancy.replace("+", "%2B");
        }
        НЕ ВАРИАНТ ИСПРАВЛЕНИЯ ОШИБКИ СИМВОЛА "+"!!!
        */
        vacancy = vacancy.replace(" ", "+");

        System.out.print("Enter the number of pages: ");
        Integer countPages = in.nextInt() + 1;

        FileWriter logfile = new FileWriter("logfile.txt");

        // Work.Ua
        Integer page = 1;
        int vacancyCount = 0;

        while(page < countPages) {
            logfile.write("\tСтраница - " + page + "\n");
            Document workua = Jsoup.connect(
                    "https://work.ua/jobs-" + vacancy + "/?page=" + page
            ).get();

            logfile.write(workua.title() + "\n");

            Elements resultOfSearch = workua.getElementsByClass("add-bottom-sm");
            Elements elements = resultOfSearch.select("a");

            for (Element element : elements) {
                logfile.write(element.attr("title") + "\n" + element.absUrl("href") + "\n");
                vacancyCount++;
            }

            page++;
        }

        System.out.print("Done! Vacancy count - " + vacancyCount + "!");
        logfile.write("\nVacancy count - " + vacancyCount + "!");
        logfile.close();
    }
}
